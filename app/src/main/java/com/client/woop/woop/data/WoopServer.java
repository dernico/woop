package com.client.woop.woop.data;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.web.NetworkFinder;
import com.client.woop.woop.web.StringDownloader;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class WoopServer {

    public interface WoopServerListener{
        void serviceFound();
    }

    private static String TAG = WoopServer.class.getSimpleName();
    private static WoopServer _server;

    private static ILogger _logger = new Logger();
    private String _serviceHostAdress;

    private WoopServer(){
    }

    public static WoopServer singelton(){
        if(_server == null){
            _logger.info(TAG, "Woop server gets created");
            _server = new WoopServer();
        }
        return _server;
    }

    public void findService(final WoopServerListener listener){

        String subnet = "http://192.168.1.";

        final List<StringDownloader> downloader = new ArrayList<>();

        for(int i = 1; i < 255; i ++){
            downloader.add( new StringDownloader(subnet + i +":8000", 500,  new StringDownloader.DownloadCompleteListener() {
                @Override
                public void completionCallBack(String uri, String result) {
                    _serviceHostAdress = uri;
                    for(int i = 0; i < downloader.size(); i++){
                        downloader.get(i).cancel(true);
                    }

                    listener.serviceFound();
                }
            }));
        }

        for(int i = 0; i < downloader.size(); i++){
            downloader.get(i).execute();
        }


        /*int steps = 15;
        for (int i=1; i < 255/steps; i+=steps){
            new NetworkFinder("192.168.0",i, steps, new NetworkFinder.NetworkFinderFoundService() {
                @Override
                public void serviceFound(String host) {
                    _serviceHostAdress = host;
                }
            });
        }*/
    }

}
