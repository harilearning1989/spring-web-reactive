package com.web.reactive.audit.aspect;

import com.web.reactive.audit.context.RequestContext;
import com.web.reactive.audit.models.ClientAudit;
import com.web.reactive.audit.models.ServiceAudit;
import com.web.reactive.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Aspect
@Component
@EnableAspectJAutoProxy(exposeProxy = true)
@Order(0)
public class AuditAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestContext requestContext;

    @Around("@annotation(com.web.reactive.audit.annotation.Audited)")
    public Object saveAuditData(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.info("============SaveAudit Data=============");
        LocalDateTime localDateTime = LocalDateTime.now();
        final Object response = proceedingJoinPoint.proceed();

        try {
            HttpServletRequest httpServletRequest = null;
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServiceAudit serviceAudit = new ServiceAudit();
            if (requestAttributes instanceof ServletRequestAttributes) {
                httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
                String requestUrl = requestContext.getRequestUrl(httpServletRequest);
                String parameters = httpServletRequest.getQueryString();
                String serviceName = requestContext.getServiceName(httpServletRequest);
                String parentAuditId = httpServletRequest.getHeader(Constants.PARENT_AUDIT_ID);
                String method = httpServletRequest.getMethod();

                /*System.out.println("requestUrl::" + requestUrl
                        + "===parameters::" + parameters
                        + "===serviceName::" + serviceName
                        + "===parentAuditId::" + parentAuditId
                        + "===method::" + method);*/
                serviceAudit.setRequestUrl(requestUrl);
                serviceAudit.setMethod(method);
                serviceAudit.setServiceName(serviceName);
            }

            HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getResponse();
            serviceAudit.setStatus(httpServletResponse.getStatus());

            List<ClientAudit> clientAudit = null;
            if (null != httpServletRequest.getAttribute(Constants.CLIENT_AUDIT_KEY)) {
                clientAudit = (List<ClientAudit>) httpServletRequest.getAttribute(Constants.CLIENT_AUDIT_KEY);
            }

            serviceAudit.setClientAuditList(clientAudit);
            System.out.println("serviceAudit::"+serviceAudit);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
