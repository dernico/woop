package com.client.woop.woop;

import android.util.Log;

/**
 * Created by nico on 10/1/2015.
 */
public class Logger implements ILogger{

    public void debug(String tag, String message){
        Log.d(tag,message);
    }

    @Override
    public void info(String tag, String message) {
        Log.i(tag,message);
    }

    @Override
    public void error(String tag, String message) {
        Log.e(tag,message);
    }


}
