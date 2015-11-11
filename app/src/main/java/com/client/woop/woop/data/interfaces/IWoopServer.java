package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.models.LocalMusicModel;
import com.client.woop.woop.models.ServerMusicModel;
import com.client.woop.woop.models.PlayingInfoModel;
import com.client.woop.woop.models.StreamModel;
import com.client.woop.woop.models.TuneInModel;

import java.util.List;


public interface IWoopServer {
    void connect();
    boolean isServerOnline();
    void findService(WoopServer.WoopServerListener callback);

    void subscribePlayingInfoChanged(WoopServer.WoopInfoChanged callback);

    PlayingInfoModel currentPlayingInfo();
    boolean isPlaying();

    void callinfo();
    void play(WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void play(ServerMusicModel model, WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void play(LocalMusicModel model, WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void pause(WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void prev(WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void next(WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void shuffle(WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void volumeUp(WoopServer.WoopDataReceived<PlayingInfoModel> callback);
    void volumeDown(WoopServer.WoopDataReceived<PlayingInfoModel> callback);

    void getMyMusic(WoopServer.WoopDataReceived<List<ServerMusicModel>> callback);

    void getSavedStreams(WoopServer.WoopDataReceived<List<StreamModel>> result);
    void playSavedStream(StreamModel stream, WoopServer.WoopDataReceived<PlayingInfoModel> callback);

    void searchTuneInStream(String query, WoopServer.WoopDataReceived<List<TuneInModel>> callback);
    void playTuneInStream(TuneInModel model, WoopServer.WoopDataReceived<PlayingInfoModel> callback);
}
