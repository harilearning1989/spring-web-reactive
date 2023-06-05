package com.web.reactive.config.filter;

import com.web.reactive.audit.models.ClientAudit;
import com.web.reactive.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.lang.NonNull;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.min;
import static java.util.UUID.randomUUID;

public class RequestLoggingFilterFunction implements ExchangeFilterFunction {

    private static final int MAX_BYTES_LOGGED = 4_096;

    private final String externalSystem = "";

    @Override
    @NonNull
    public Mono<ClientResponse> filter(@NonNull ClientRequest clientRequest, @NonNull ExchangeFunction next) {
        /*if (!log.isDebugEnabled()) {
            return next.exchange(request);
        }*/

        var clientRequestId = randomUUID().toString();

        var requestLogged = new AtomicBoolean(false);
        var responseLogged = new AtomicBoolean(false);

        var capturedRequestBody = new StringBuilder();
        var capturedResponseBody = new StringBuilder();

        var stopWatch = new StopWatch();
        stopWatch.start();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ClientAudit clientAudit = new ClientAudit();

        return next
                .exchange(ClientRequest.from(clientRequest).body(new BodyInserter<>() {
                    @Override
                    @NonNull
                    public Mono<Void> insert(@NonNull ClientHttpRequest req, @NonNull Context context) {
                        return clientRequest.body().insert(new ClientHttpRequestDecorator(req) {
                            @Override
                            @NonNull
                            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                                return super.writeWith(Flux.from(body).doOnNext(data -> capturedRequestBody.append(extractBytes(data)))); // number of bytes appended is maxed in real code
                            }
                        }, context);
                    }
                }).build())
                .doOnNext(response -> {
                            /*System.out.println("clientRequestId::" + clientRequestId
                                    + "===clientRequestMethod::" + clientRequest.method()
                                    + "===clientRequestUrl::" + clientRequest.url()
                                    + "===clientRequestHeaders::" + clientRequest.headers()
                                    + "===clientRequestBody::" + capturedRequestBody.toString());*/

                            MultiValueMap<String, String> multiValueMap = clientRequest.headers();

                            var url = String.valueOf(clientRequest.url());
                            var serviceName = url.substring(url.lastIndexOf("/") + 1);

                            clientAudit.setRequest(capturedRequestBody.toString());
                            clientAudit.setMethod(clientRequest.method().name());
                            clientAudit.setRequestUrl(clientRequest.url().toString());
                            clientAudit.setServiceName(serviceName);
                        }
                )
                .doOnError(error -> {
                    System.out.println("clientRequestId::" + clientRequestId
                            + "===clientRequestMethod::" + clientRequest.method()
                            + "===clientRequestUrl::" + clientRequest.url()
                            + "===clientRequestHeaders::" + clientRequest.headers()
                            + "===Error Message::" + error.getMessage());
                    clientAudit.setErrorMessage(error.getMessage());
                })
                .map(response -> response.mutate().body(transformer -> transformer
                                .doOnNext(body -> capturedResponseBody.append(extractBytes(body))) // number of bytes appended is maxed in real code
                                .doOnTerminate(() -> {
                                    if (stopWatch.isRunning()) {
                                        stopWatch.stop();
                                    }
                                })
                                .doOnComplete(() -> {
                                    System.out.println("clientRequestId::" + clientRequestId
                                            + "===Duration::" + stopWatch.getTotalTimeMillis()
                                            + "===statusCode::" + response.statusCode().value()
                                            + "===clientResponseHeaders::" + response.headers()
                                            + "===clientResponseBody::" + capturedResponseBody.toString());

                                    List<ClientAudit> clientAuditList = null;
                                    if (requestAttributes instanceof ServletRequestAttributes) {
                                        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
                                        if (null != httpServletRequest) {
                                            if (null != httpServletRequest.getAttribute(Constants.CLIENT_AUDIT_KEY)) {
                                                clientAuditList = (List<ClientAudit>) httpServletRequest.getAttribute(Constants.CLIENT_AUDIT_KEY);
                                            } else {
                                                clientAuditList = new ArrayList<>();
                                            }
                                            clientAudit.setDuration((int) stopWatch.getTotalTimeMillis());
                                            clientAudit.setStatus(response.statusCode().value());
                                            clientAudit.setResponse(capturedResponseBody.toString());

                                            clientAuditList.add(clientAudit);
                                            httpServletRequest.setAttribute(Constants.CLIENT_AUDIT_KEY, clientAuditList);
                                        }
                                    }
                                })
                                .doOnError(error -> {
                                            System.out.println("externalSystem" + externalSystem
                                                    + "===clientRequestId::" + clientRequestId
                                                    + "===clientRequestExecutionTimeInMillis::" + stopWatch.getTotalTimeMillis()
                                                    + "===clientResponseStatusCode::" + response.statusCode().value()
                                                    + "===clientResponseHeaders::" + response.headers() // filtered in real code
                                                    + "===clientRequestExecutionTimeInMillis::" + stopWatch.getTotalTimeMillis()
                                                    + "===clientErrorMessage::" + error.getMessage()
                                                    + "===clientResponseBody::" + capturedResponseBody.toString());
                                        }
                                )
                        ).build()
                );
    }

    private static String extractBytes(DataBuffer data) {
        int currentReadPosition = data.readPosition();
        var numberOfBytesLogged = min(data.readableByteCount(), MAX_BYTES_LOGGED);
        var bytes = new byte[numberOfBytesLogged];
        data.read(bytes, 0, numberOfBytesLogged);
        data.readPosition(currentReadPosition);
        return new String(bytes);
    }

}