package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.models.PlayingInfo;
import com.client.woop.woop.models.StreamModel;
import com.client.woop.woop.models.TuneInModel;

import java.util.List;


public interface IWoopServer {
    boolean isServiceAdressSet();
    void findService(WoopServer.WoopServerListener callback);
    void resetService();

    void subscribePlayingInfoChanged(WoopServer.WoopInfoChanged callback);

    PlayingInfo currentPlayingInfo();
    boolean isPlaying();

    void callinfo();
    void play(WoopServer.WoopDataReceived<PlayingInfo> callback);
    void pause(WoopServer.WoopDataReceived<PlayingInfo> callback);
    void prev(WoopServer.WoopDataReceived<PlayingInfo> callback);
    void next(WoopServer.WoopDataReceived<PlayingInfo> callback);
    void shuffle(WoopServer.WoopDataReceived<PlayingInfo> callback);
    void volumeUp(WoopServer.WoopDataReceived<PlayingInfo> callback);
    void volumeDown(WoopServer.WoopDataReceived<PlayingInfo> callback);

    void getSavedStreams(WoopServer.WoopDataReceived<List<StreamModel>> result);
    void playSavedStream(StreamModel stream, WoopServer.WoopDataReceived<PlayingInfo> callback);

    void searchStream(String query, WoopServer.WoopDataReceived<List<TuneInModel>> callback);
}
