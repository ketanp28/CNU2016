package com.devfactory.cnu.ketan.spring.controller.model;

/**
 * Created by ketanpatil on 11/07/16.
 */
public class LogJson {
    private long timestamp;
    private String url;
    private String parameter;
    private int responseCode;
    private String ipAddress;

    public LogJson(long timestamp, String url, String parameter, int responseCode, String ipAddress){
        this.timestamp = timestamp;
        this.url = url;
        this.parameter = parameter;
        this.responseCode = responseCode;
        this.ipAddress = ipAddress;

    }


    public String ToJson(){
        return "timestamp :" + this.timestamp + "url : " + this.url +
                "parameter : " + this.parameter + "responceCode : " + this.responseCode +
                "ipAddress : " + this.ipAddress;
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


}
