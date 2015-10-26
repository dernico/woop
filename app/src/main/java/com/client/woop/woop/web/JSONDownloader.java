package com.client.woop.woop.web;


import android.content.res.Resources;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONDownloader {

    public interface JSONDownloadCompleteListener{
        void jsonComplete(JSONObject json);
        void errorOccured(Exception ex);
    }

    private static String TAG = JSONDownloader.class.getSimpleName();

    private HttpRequest _downloader;
    private ILogger _logger;

    public JSONDownloader(HttpOptions options, final JSONDownloadCompleteListener jsonListener){
        _logger = new Logger();

        if(jsonListener == null){
            throw new Resources.NotFoundException("listener was null. You need to have listener");
        }

        _downloader = new HttpRequest(options, new HttpRequest.DownloadCompleteListener() {
            @Override
            public void completionCallBack(HttpOptions options, String result) {
                if(result != null) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
                    } catch (JSONException e) {
                        options.setError(e);
                    }
                    if(options.hasError){
                        jsonListener.errorOccured(options.getError());
                    }else{
                        jsonListener.jsonComplete(json);
                    }
                }
            }

            @Override
            public void errorCallBack(HttpOptions options) {
                jsonListener.errorOccured(options.getError());
            }
        });
    }

    public void execute(){
        _downloader.execute();
    }

}
