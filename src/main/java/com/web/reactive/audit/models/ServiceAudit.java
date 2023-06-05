package com.web.reactive.audit.models;

import java.util.List;

public class ServiceAudit {

    private String requestUrl;
    private String method;
    private String serviceName;
    private int status;
    private int duration;
    private Object request;
    private Object response;
    private String errorCode;
    private String errorMessage;

    List<ClientAudit> clientAuditList;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<ClientAudit> getClientAuditList() {
        return clientAuditList;
    }

    public void setClientAuditList(List<ClientAudit> clientAuditList) {
        this.clientAuditList = clientAuditList;
    }

    @Override
    public String toString() {
        return "ServiceAudit{" +
                "requestUrl='" + requestUrl + '\'' +
                ", method='" + method + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", request=" + request +
                ", response=" + response +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", clientAuditList=" + clientAuditList +
                '}';
    }
}
