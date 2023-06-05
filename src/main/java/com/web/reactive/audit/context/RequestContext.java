package com.web.reactive.audit.context;


import com.web.reactive.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class RequestContext {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public String getRequestUrl(HttpServletRequest httpServletRequest){
        StringBuilder requestUrl = new StringBuilder();

        try{
            if(null != httpServletRequest && null != httpServletRequest.getRequestURL()){
                requestUrl.append(httpServletRequest.getRequestURL().toString());
                if(null != httpServletRequest.getQueryString()){
                    String queryString = URLDecoder.decode(httpServletRequest.getQueryString(), "UTF-8");
                    requestUrl.append(Constants.QUESTION_MARK).append(queryString);
                }
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            LOGGER.error("Exception in debugging query string : {0}",e);
        }
        return requestUrl.toString();
    }

    public String getServiceName(HttpServletRequest httpServletRequest) {
        String serviceName = null;
        if(null != httpServletRequest && null != httpServletRequest.getRequestURI()){
            String requestUri = httpServletRequest.getRequestURI();
            serviceName = requestUri.substring(requestUri.lastIndexOf(Constants.FORWARD_SLASH)+1);
        }
        return serviceName;
    }
}
