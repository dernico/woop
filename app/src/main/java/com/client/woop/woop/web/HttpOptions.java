package com.client.woop.woop.web;

import java.util.HashMap;

public class HttpOptions{
    private static int defaultTimeout = 1000;

    public String url;
    public int timeout;
    public String type;
    public HashMap<String, String> postData;

    public boolean hasError;
    private Exception error;


    public HttpOptions(String url){
        this.hasError = false;
        this.url = url;
        this.timeout = defaultTimeout;
        this.type = HttpRequestType.GET.toString();
    }

    public HttpOptions(String url, int timeout){
        this.hasError = false;
        this.url = url;
        this.timeout = timeout;
        this.type = HttpRequestType.GET.toString();
    }


    public HttpOptions(String url, HttpRequestType type){
        this.hasError = false;
        this.url = url;
        this.timeout = defaultTimeout;
        this.type = type.toString();
    }

    public void setPostData(HashMap<String,String> data){
        this.postData = data;
    }

    public void setError(Exception ex){
        this.error = ex;
        this.hasError = true;
    }

    public Exception getError(){
        return error;
    }

}