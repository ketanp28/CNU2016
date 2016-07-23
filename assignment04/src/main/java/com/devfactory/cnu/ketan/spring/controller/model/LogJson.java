package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.Column;

/**
 * Created by ketanpatil on 11/07/16.
 */
public class LogJson {
    private long timestamp;
    private String url;
    @Column(name = "parameters")
    private String parameter;
    @Column(name = "response_code")
    private int responseCode;
    @Column(name = "request_ip_address")
    private String ipAddress;
    @Column(name = "request_duration_ms ")
    private long diff;
    @Column(name = "request_type")
    private String requestType;

    public LogJson(long timestamp, String url, String parameter, int responseCode, String ipAddress, long diff,String requestType){
        this.timestamp = timestamp;
        this.url = url;
        this.parameter = parameter;
        this.responseCode = responseCode;
        this.ipAddress = ipAddress;
        this.diff = diff;
        this.requestType = requestType;

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDiff() {
        return diff;
    }

    public void setDiff(long diff) {
        this.diff = diff;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
