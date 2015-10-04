package com.client.woop.woop.data;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.web.NetworkFinder;

import java.net.InetAddress;

/**
 * Created by nico on 9/30/2015.
 */
public class WoopServer {

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

    public void findService(){
        new NetworkFinder("192.168.0",1, 100, new NetworkFinder.NetworkFinderFoundService() {
            @Override
            public void serviceFound(String host) {
                _serviceHostAdress = host;
            }
        }).execute();

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
