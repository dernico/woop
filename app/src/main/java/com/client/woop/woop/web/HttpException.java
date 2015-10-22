package com.client.woop.woop.web;

public class HttpException extends Exception {

    private Exception _originalException;

    public HttpException(Exception ex){
        _originalException = ex;
    }

    private Exception get_originalException(){
        return _originalException;
    }

}
