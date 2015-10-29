package com.client.woop.woop.web;

import java.io.File;
import java.util.HashMap;

public class HttpOptions{
    private static int defaultTimeout = 3000;

    public String url;
    public int timeout;
    public String type;
    public HashMap<String, String> postData;
    private String boundary =  "*****";
    private File file;
    private String filename;

    public String crlf = "\r\n";
    public String twoHyphens = "--";

    public boolean hasError;
    private Exception error;


    public HttpOptions(String url){
        this.hasError = false;
        this.url = url;
        this.timeout = defaultTimeout;
        this.type = HttpRequestType.GET.toString();
        this.boundary = "===" + System.currentTimeMillis() + "===";
    }

    public HttpOptions(String url, int timeout){
        this(url);
        this.timeout = timeout;
    }


    public HttpOptions(String url, HttpRequestType type){
        this(url);
        this.type = type.toString();
    }

    public void setPostData(HashMap<String,String> data){
        this.postData = data;
    }

    public void setFile(String filename, File file){
        this.file = file;
        this.filename = filename;
    }
    public File getFile(){
        return this.file;
    }

    public String getFilename(){
        return this.filename;
    }

    public void setBoundary(String boundary){
        this.boundary = boundary;
    }

    public String getBoundary(){
        return this.boundary;
    }

    public void setError(Exception ex){
        this.error = ex;
        this.hasError = true;
    }

    public Exception getError(){
        return error;
    }

}