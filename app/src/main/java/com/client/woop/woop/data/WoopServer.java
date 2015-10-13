package com.client.woop.woop.data;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.web.StringDownloader;

import java.util.ArrayList;
import java.util.List;


public class WoopServer implements IWoopServer{

    private static String TAG = WoopServer.class.getSimpleName();
    private static String SERVICE_HOST_ADDRESS = "Service_Host_Address";
    private static WoopServer _server;

    private static ILogger _logger = new Logger();
    private IClientDataStorage _storage;
    private String _serviceHostAdress;


    public interface WoopServerListener{
        void serviceFound();
    }


    private WoopServer(IClientDataStorage storage){
        _storage = storage;
    }

    public static WoopServer singelton(IClientDataStorage storage){
        if(_server == null){
            _logger.info(TAG, "Woop server gets created");
            _server = new WoopServer(storage);
        }
        return _server;
    }

    @Override
    public boolean isServiceAdressSet() {
        _serviceHostAdress = _storage.getString(SERVICE_HOST_ADDRESS);
        if(_serviceHostAdress == null){
            return false;
        }
        return true;
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
                    _storage.putString(SERVICE_HOST_ADDRESS, _serviceHostAdress);
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

    @Override
    public void resetService() {
        _storage.removeKey(SERVICE_HOST_ADDRESS);
    }

}
