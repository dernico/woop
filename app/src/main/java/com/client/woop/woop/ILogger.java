package com.client.woop.woop;

/**
 * Created by nico on 10/1/2015.
 */
public interface ILogger {

    void debug(String tag, String message);
    void info(String tag, String message);
    void error(String tag, String message);

}
