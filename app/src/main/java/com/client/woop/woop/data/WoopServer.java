package com.client.woop.woop.data;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.contracts.YoutubeContract;
import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.data.interfaces.IDeviceData;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.extensions.YoutubeList;
import com.client.woop.woop.models.KeyValueModel;
import com.client.woop.woop.models.LocalMusicModel;
import com.client.woop.woop.models.ServerMusicModel;
import com.client.woop.woop.models.PlayingInfoModel;
import com.client.woop.woop.models.StreamModel;
import com.client.woop.woop.models.TuneInModel;
import com.client.woop.woop.models.YouTubeModel;
import com.client.woop.woop.web.HttpOptions;
import com.client.woop.woop.web.HttpRequest;
import com.client.woop.woop.web.HttpRequestType;
import com.client.woop.woop.web.JSONDownloader;
import com.client.woop.woop.web.FileUploader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

    private IKeyValueStorage _storage;
    private IDeviceData _deviceData;
    private PlayingInfoModel _currentPlayinginfo;
    private HashMap<String, WoopInfoChanged> _infoChangedCallbacks;
    private IServerAvailable _serverAvailableCallback;
    private boolean _serverStatus;

    public interface WoopServerListener{
        void serviceFound();
    }

    public interface WoopDataReceived<T>{
        void dataReceived(T result);
        void errorReceived(Exception ex);
    }

    public interface IServerAvailable {
        void serverAvailable(boolean available);
    }

    public interface WoopInfoChanged{
        void infoChanged(PlayingInfoModel info);
    }

    private WoopServer(IKeyValueStorage storage, IDeviceData deviceData){
        _storage = storage;
        _deviceData = deviceData;
        _infoChangedCallbacks = new HashMap<>();
    }

    public static WoopServer singelton(IKeyValueStorage storage, IDeviceData deviceData){
        if(_server == null){
            _logger.info(TAG, "Woop server gets created");
            _server = new WoopServer(storage, deviceData);
        }
        return _server;
    }

    private String urlEncode(String encoding){
        try {
            encoding = URLEncoder.encode(encoding, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //Don't know why, but try the search without urlencoder
            _logger.info(TAG, "Could not Urlencode: " + encoding);
        }
        return  encoding;
    }

    @Override
    public void connect() {

        _storage.getString(SHARED_DATA_SERVICE_HOST_ADDRESS, new KeyValueStoreDB.IKeyValueStoreCallback() {
            @Override
            public void done(KeyValueModel result) {
                if(result != null){
                    _serviceHostAdress = result.value;
                    checkIfServerIsOnline();
                }else{
                    setServerAvailableStatus(false);
                }

            }
        });
    }

    @Override
    public boolean isServerOnline() {
        return _serverStatus;
    }

    public void setServerAvailableCallback(IServerAvailable callback){
        _serverAvailableCallback = callback;
    }

    private void checkIfServerIsOnline(){
        new HttpRequest(new HttpOptions(_serviceHostAdress), new HttpRequest.DownloadCompleteListener() {
            @Override
            public void completionCallBack(HttpOptions options, String result) {
                setServerAvailableStatus(result != null);
            }

            @Override
            public void errorCallBack(HttpOptions options) {
                setServerAvailableStatus(false);
            }
        }).execute();
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
                        _storage.putString(SHARED_DATA_SERVICE_HOST_ADDRESS, _serviceHostAdress, new KeyValueStoreDB.IKeyValueStoreCallback() {
                            @Override
                            public void done(KeyValueModel result) {
                                listener.serviceFound();
                            }
                        });
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
    public String getServiceAddress() {
        return _serviceHostAdress;
    }

    @Override
    public void subscribePlayingInfoChanged(WoopInfoChanged callback) {
        if(_infoChangedCallbacks.containsKey(callback.getClass().getName())){
            _infoChangedCallbacks.remove(callback.getClass().getName());
        }
        _infoChangedCallbacks.put(callback.getClass().getName(), callback);

        if(callback != null){
            PlayingInfoModel info = currentPlayingInfo();
            if(info != null) {
                callInfoChangeListener(info);
            }
        }
    }

    private void callInfoChangeListener(PlayingInfoModel info){
        for(WoopInfoChanged callback : _infoChangedCallbacks.values()){
            callback.infoChanged(info);
        }
    }

    @Override
    public PlayingInfoModel currentPlayingInfo() {
        if(_currentPlayinginfo == null){
            this.callinfo();
        }
        return _currentPlayinginfo;
    }

    @Override
    public boolean isPlaying() {
        if(currentPlayingInfo() == null){
            return false;
        }
        return currentPlayingInfo().IsPlaying;
    }

    @Override
    public void callinfo() {
        this.playControlCall("/api/music/info", null);
    }

    @Override
    public void play(final WoopServer.WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/play", callback);
    }

    @Override
    public void play(ServerMusicModel model, WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/play?id=" + model.get_id(), callback);
    }

    @Override
    public void play(LocalMusicModel model, final WoopDataReceived<PlayingInfoModel> callback) {

        final File file = new File(model.get_uri());

        String filename = file.getName();

        filename = urlEncode(filename);

        String url = _serviceHostAdress + "/api/music/checkandplay?filename=" + filename;
        HttpOptions options = new HttpOptions(url);
        new HttpRequest(options, new HttpRequest.DownloadCompleteListener() {
            @Override
            public void completionCallBack(HttpOptions options, String result) {
                if(result.compareTo("false") == 0){
                    UploadFile(file, callback);
                }else{
                    try {
                        PlayingInfoModel info = PlayingInfoModel.createFromJson(new JSONObject(result));
                        setPlayingInfo(info);
                        callback.dataReceived(info);
                    } catch (JSONException e) {
                        callback.errorReceived(e);
                    }
                }
            }

            @Override
            public void errorCallBack(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        }).execute();
    }

    private void UploadFile(File file, final WoopDataReceived<PlayingInfoModel> callback) {
        String url = _serviceHostAdress + "/api/music/upload";

        new FileUploader(url, "nexttrack", file, new FileUploader.FileUploadListener() {
            @Override
            public void fileUploaded(String result) {
                try {
                    PlayingInfoModel info = PlayingInfoModel.createFromJson(new JSONObject(result));
                    setPlayingInfo(info);
                    callback.dataReceived(info);
                } catch (JSONException e) {
                    callback.errorReceived(e);
                }
            }

            @Override
            public void uploadFailed(Exception ex) {
                callback.errorReceived(ex);
            }
        }).execute();
    }

    @Override
    public void pause(WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/pause", callback);
    }

    @Override
    public void prev(WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/prev", callback);
    }

    @Override
    public void next(WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/next", callback);
    }

    @Override
    public void shuffle(WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/toggleRandom", callback);
    }

    @Override
    public void volumeUp(WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/volumeUp", callback);
    }

    @Override
    public void volumeDown(WoopDataReceived<PlayingInfoModel> callback) {
        this.playControlCall("/api/music/volumeDown", callback);
    }

    @Override
    public void getMyMusic(final WoopDataReceived<List<ServerMusicModel>> callback) {
        HttpOptions options = new HttpOptions(_serviceHostAdress + "/api/music/listcomplete");
        makeJSONRequest(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                try {
                    int count = json.getInt("count");
                    JSONArray liste = json.getJSONArray("list");
                    List<ServerMusicModel> mymusic = new ArrayList<>();
                    for (int i = 0; i < liste.length(); i++) {
                        mymusic.add(ServerMusicModel.create(liste.getJSONObject(i)));
                    }
                    callback.dataReceived(mymusic);
                } catch (JSONException e) {
                    callback.errorReceived(e);
                }
            }

            @Override
            public void errorOccured(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        });
    }

    @Override
    public void getSavedStreams(final WoopDataReceived<List<StreamModel>> result) {

        makeJSONRequest(new HttpOptions(_serviceHostAdress + "/api/music/streams"), new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                List<StreamModel> streams = new ArrayList<StreamModel>();
                try {
                    JSONArray aStreams = json.getJSONArray("streams");
                    for (int i = 0; i < aStreams.length(); i++) {
                        streams.add(StreamModel.createFromJson(aStreams.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    _logger.info(TAG, e.toString());
                }
                result.dataReceived(streams);
            }

            @Override
            public void errorOccured(HttpOptions options) {
                result.errorReceived(options.getError());
            }
        });
    }

    @Override
    public void playSavedStream(StreamModel stream, final WoopDataReceived<PlayingInfoModel> callback) {
        HttpOptions options = new HttpOptions(
                _serviceHostAdress + "/api/music/playStream",
                HttpRequestType.POST);

        HashMap<String, String> data = new HashMap<>();
        data.put("stream", stream.getStream());
        options.setPostData(data);
        makeJSONRequest(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {

                setPlayingInfo(PlayingInfoModel.createFromJson(json));
                callback.dataReceived(_currentPlayinginfo);
            }

            @Override
            public void errorOccured(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        });
    }

    @Override
    public void searchTuneInStream(String query, final WoopDataReceived<List<TuneInModel>> callback) {
        query = urlEncode(query);

        HttpOptions options = new HttpOptions(
                _serviceHostAdress + "/api/tunein/search?search="+query,
                10000 // wait ten seconds, you never know :)
        );

        makeJSONRequest(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                try {
                    List<TuneInModel> streams = new ArrayList();
                    JSONArray results = json.getJSONArray("result");
                    for(int i = 0; i < results.length(); i++){
                        streams.add(TuneInModel.create(results.getJSONObject(i)));
                    }
                    callback.dataReceived(streams);
                } catch (JSONException e) {
                    setServerAvailableStatus(false);
                    callback.errorReceived(e);
                }
            }

            @Override
            public void errorOccured(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        });
    }

    @Override
    public void playTuneInStream(TuneInModel model, final WoopDataReceived<PlayingInfoModel> callback) {
        HttpOptions options = new HttpOptions(
                _serviceHostAdress + "/api/tunein/play",
                HttpRequestType.POST
        );
        try {

            HashMap<String, String> data = new HashMap<>();
            data.put("item", model.toJSONString());
            options.setPostData(data);

        } catch (JSONException e) {
            callback.errorReceived(e);
            return;
        }

        makeJSONRequest(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                PlayingInfoModel info = PlayingInfoModel.createFromJson(json);
                setPlayingInfo(info);
                callback.dataReceived(info);
            }

            @Override
            public void errorOccured(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        });
    }

    @Override
    public void youtubeSearch(String searchterm, final WoopDataReceived<YoutubeList> callback) {

        searchterm = urlEncode(searchterm);
        String url = _serviceHostAdress + "/api/youtube/search?search=" + searchterm;
        HttpOptions options = new HttpOptions(url);
        makeJSONRequest(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                try {
                    YoutubeList result = new YoutubeList();
                    result.Query = json.getString(YoutubeContract.query);
                    result.NextPageToken = json.getString(YoutubeContract.nextPageToken);
                    JSONArray tracks = json.getJSONArray(YoutubeContract.tracks);
                    for(int i = 0; i < tracks.length(); i++){
                        YouTubeModel model = YouTubeModel.createFromJSON(tracks.getJSONObject(i));
                        result.add(model);
                    }
                    callback.dataReceived(result);
                } catch (JSONException e) {
                    callback.errorReceived(e);
                }
            }

            @Override
            public void errorOccured(HttpOptions options) {
                callback.errorReceived(options.getError());
            }
        });

    }

    //private functions

    private void playControlCall(String apiPart, final WoopDataReceived<PlayingInfoModel> callback){

        HttpOptions options = new HttpOptions(_serviceHostAdress + apiPart);
        makeJSONRequest(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                setPlayingInfo(PlayingInfoModel.createFromJson(json));
                if (callback != null) {
                    callback.dataReceived(_currentPlayinginfo);
                }
            }

            @Override
            public void errorOccured(HttpOptions options) {
                if (callback != null) {
                    callback.errorReceived(options.getError());
                }
            }
        });
    }


    private void makeJSONRequest(HttpOptions options, final JSONDownloader.JSONDownloadCompleteListener callback){
        new JSONDownloader(options, new JSONDownloader.JSONDownloadCompleteListener() {
            @Override
            public void jsonComplete(HttpOptions options, JSONObject json) {
                setServerAvailableStatus(true);
                if(callback != null) {
                    callback.jsonComplete(options, json);
                }
            }

            @Override
            public void errorOccured(HttpOptions options) {
                if (!(options.getError() instanceof JSONException)){
                    setServerAvailableStatus(false);
                }
                if(callback != null) {
                    callback.errorOccured(options);
                }
            }
        }).execute();
    }

    private void setServerAvailableStatus(boolean status){
        _serverStatus = status;
        if(_serverAvailableCallback != null) {
            _serverAvailableCallback.serverAvailable(status);
        }
    }

    private void setPlayingInfo(PlayingInfoModel info){
        _currentPlayinginfo = info;
        if( _infoChangedCallbacks != null && _infoChangedCallbacks.size() > 0){
            callInfoChangeListener(info);
        }
    }
}
