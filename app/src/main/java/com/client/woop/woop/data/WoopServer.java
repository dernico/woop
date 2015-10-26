package com.client.woop.woop.data;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.data.interfaces.IClientDataStorage;
import com.client.woop.woop.data.interfaces.IDeviceData;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.models.PlayingInfo;
import com.client.woop.woop.models.StreamModel;
import com.client.woop.woop.models.TuneInModel;
import com.client.woop.woop.web.HttpOptions;
import com.client.woop.woop.web.HttpRequest;
import com.client.woop.woop.web.HttpRequestType;
import com.client.woop.woop.web.JSONDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WoopServer implements IWoopServer {

    private static String TAG = WoopServer.class.getSimpleName();
    private static String SHARED_DATA_SERVICE_HOST_ADDRESS = "Service_Host_Address";
    private static WoopServer _server;

    private static ILogger _logger = new Logger();
    private String _serviceHostAdress;

    private IClientDataStorage _storage;
    private IDeviceData _deviceData;
    private PlayingInfo _currentPlayinginfo;

    public interface WoopServerListener{
        void serviceFound();
    }

    public interface WoopDataReceived<T>{
        void dataReceived(T result);
        void errorReceived(Exception ex);
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
        _serviceHostAdress = _storage.getString(SHARED_DATA_SERVICE_HOST_ADDRESS);
        if(_serviceHostAdress == null){
            return false;
        }
        return true;
    }

    @Override
    public void findService(final WoopServerListener listener){

        String subnet = _deviceData.getIPAddress();
        subnet = "http://" + subnet.substring(0, subnet.lastIndexOf('.')+1);
        final List<HttpRequest> downloader = new ArrayList<>();

        for(int i = 1; i < 255; i ++){
            downloader.add( new HttpRequest(new HttpOptions(subnet + i +":8000", 500),  new HttpRequest.DownloadCompleteListener() {
                @Override
                public void completionCallBack(HttpOptions options, String result) {
                    if(result != null) {
                        _serviceHostAdress = options.url;
                        for (int i = 0; i < downloader.size(); i++) {
                            downloader.get(i).cancel(true);
                        }
                        _storage.putString(SHARED_DATA_SERVICE_HOST_ADDRESS, _serviceHostAdress);
                        listener.serviceFound();
                    }
                }

                @Override
                public void errorCallBack(HttpOptions options) {
                    _logger.info(TAG, options.getError().toString());
                }
            }));
        }

        for(int i = 0; i < downloader.size(); i++){
            downloader.get(i).execute();
        }

    }

    @Override
    public void resetService() {
        _storage.removeKey(SHARED_DATA_SERVICE_HOST_ADDRESS);
    }

    @Override
    public PlayingInfo currentPlayingInfo() {
        return _currentPlayinginfo;
    }

    @Override
    public boolean isPlaying() {
        if(_currentPlayinginfo == null){
            return false;
        }
        return _currentPlayinginfo.IsPlaying;
    }

    private void playControlCall(String apiPart, final WoopDataReceived<PlayingInfo> callback){

        HttpOptions options = new HttpOptions(_serviceHostAdress + apiPart);
        new JSONDownloader(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(JSONObject json) {
                _currentPlayinginfo = PlayingInfo.createFromJson(json);
                callback.dataReceived(_currentPlayinginfo);
            }

            @Override
            public void errorOccured(Exception ex) {
                callback.errorReceived(ex);
            }
        }).execute();
    }

    @Override
    public void play(final WoopServer.WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/play", callback);
    }

    @Override
    public void pause(WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/pause", callback);
    }

    @Override
    public void prev(WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/prev", callback);
    }

    @Override
    public void next(WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/next", callback);
    }

    @Override
    public void shuffle(WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/toggleRandom", callback);
    }

    @Override
    public void volumeUp(WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/volumeUp", callback);
    }

    @Override
    public void volumeDown(WoopDataReceived<PlayingInfo> callback) {
        this.playControlCall("/api/music/volumeDown", callback);
    }


    @Override
    public void getSavedStreams(final WoopDataReceived<List<StreamModel>> result) {

        new JSONDownloader(new HttpOptions(_serviceHostAdress + "/api/music/streams"), new JSONDownloader.JSONDownloadCompleteListener() {
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

            @Override
            public void errorOccured(Exception ex) {
                result.errorReceived(ex);
            }
        }).execute();
    }

    @Override
    public void playSavedStream(StreamModel stream, final WoopDataReceived<PlayingInfo> callback) {
        HttpOptions options = new HttpOptions(
                _serviceHostAdress + "/api/music/playStream",
                HttpRequestType.POST);

        HashMap<String, String> data = new HashMap<>();
        data.put("stream", stream.getStream());
        options.setPostData(data);
        new JSONDownloader(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(JSONObject json) {

                _currentPlayinginfo = PlayingInfo.createFromJson(json);
                callback.dataReceived(_currentPlayinginfo);
            }

            @Override
            public void errorOccured(Exception ex) {
                callback.errorReceived(ex);
            }
        }).execute();
    }

    @Override
    public void searchStream(String query, final WoopDataReceived<List<TuneInModel>> callback) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //Don't know why, but try the search without urlencoder
        }

        HttpOptions options = new HttpOptions(
                _serviceHostAdress + "/api/tunein/search?search="+query
        );

        new HttpRequest(options, new HttpRequest.DownloadCompleteListener() {
            @Override
            public void completionCallBack(HttpOptions options, String result) {
                try {
                    List<TuneInModel> streams = new ArrayList();
                    JSONObject json = new JSONObject(result);
                    JSONArray results = json.getJSONArray("result");
                    for(int i = 0; i < results.length(); i++){
                        streams.add(TuneInModel.create(results.getJSONObject(i)));
                    }
                    callback.dataReceived(streams);
                } catch (JSONException e) {
                    callback.errorReceived(e);
                }
            }

            @Override
            public void errorCallBack(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        }).execute();
    }

}
