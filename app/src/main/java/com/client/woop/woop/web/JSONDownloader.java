package com.client.woop.woop.web;


public class JSONDownloader {

    private StringDownloader downloader;

    public JSONDownloader(String link, final StringDownloader.DownloadCompleteListener listener){
        downloader = new StringDownloader(link, 1000, new StringDownloader.DownloadCompleteListener() {
            @Override
            public void completionCallBack(String uri, String html) {
                //JSONObject json = new JSONObject(html);
                
            }
        });
    }

}
