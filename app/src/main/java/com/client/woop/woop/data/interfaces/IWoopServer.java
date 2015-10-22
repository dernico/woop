package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.models.PlayingInfo;
import com.client.woop.woop.models.StreamModel;

import java.util.List;


public interface IWoopServer {
    boolean isServiceAdressSet();
    void findService(WoopServer.WoopServerListener listener);
    void resetService();

    void getSavedStreams(WoopServer.WoopDataReceived<List<StreamModel>> result);
    void playSavedStream(StreamModel stream, WoopServer.WoopDataReceived<PlayingInfo> listener);
}
