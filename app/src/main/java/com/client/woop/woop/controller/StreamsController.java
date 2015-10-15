package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IStreamsView;
import com.client.woop.woop.models.StreamModel;

import java.util.List;

public class StreamsController {

    private IStreamsView _view;
    private IWoopServer _woop;

    public StreamsController(IStreamsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }

    public void loadStreams(){

        _woop.getSavedStreams(new WoopServer.WoopDataReceived<List<StreamModel>>() {
            @Override
            public void dataReceived(List<StreamModel> result) {
                _view.setStreams(result);
            }
        });
    }


}
