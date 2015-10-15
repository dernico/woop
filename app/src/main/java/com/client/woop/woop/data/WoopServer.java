package com.client.woop.woop.data;

import android.net.Uri;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.data.interfaces.IClientDataStorage;
import com.client.woop.woop.data.interfaces.IDeviceData;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.models.StreamModel;
import com.client.woop.woop.web.JSONDownloader;
import com.client.woop.woop.web.StringDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WoopServer implements IWoopServer {

    private static String TAG = WoopServer.class.getSimpleName();
    private static String SERVICE_HOST_ADDRESS = "Service_Host_Address";
    private static WoopServer _server;

    private static ILogger _logger = new Logger();
    private String _serviceHostAdress;

    private IClientDataStorage _storage;
    private IDeviceData _deviceData;

    public interface WoopServerListener{
        void serviceFound();
    }

    public interface WoopDataReceived<T>{
        void dataReceived(T result);
    }


    private WoopServer(IClientDataStorage storage, IDeviceData deviceData){
        _storage = storage;
        _deviceData = deviceData;
    }

    public static WoopServer singelton(IClientDataStorage storage, IDeviceData deviceData){
        if(_server == null){
            _logger.info(TAG, "Woop server gets created");
            _server = new WoopServer(storage, deviceData);
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

    @Override
    public void findService(final WoopServerListener listener){

        String subnet = _deviceData.getIPAddress();
        subnet = "http://" + subnet.substring(0, subnet.lastIndexOf('.')+1);
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

    }

    @Override
    public void resetService() {
        _storage.removeKey(SERVICE_HOST_ADDRESS);
    }

    @Override
    public void getSavedStreams(final WoopDataReceived<List<StreamModel>> result) {

        new JSONDownloader(_serviceHostAdress + "/api/music/streams", new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(JSONObject json) {
                List<StreamModel> streams = new ArrayList<StreamModel>();
                try {
                    JSONArray aStreams = json.getJSONArray("streams");
                    for(int i = 0; i < aStreams.length(); i++){
                        streams.add(StreamModel.createFromJson(aStreams.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    _logger.info(TAG, e.toString());
                }
                result.dataReceived(streams);
            }
        }).execute();
    }

}
