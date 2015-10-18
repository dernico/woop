package com.client.woop.woop.web;

/**
 * Created by nico on 10/18/2015.
 */
public enum HttpRequestType {
    GET{
        public String toString(){
            return "GET";
        }
    },

    POST {
        public String toString(){
            return "POST";
        }
    }
}
