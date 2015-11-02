package com.client.woop.woop.controller;

import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IStreamsView;

public class StreamsController {

    private IStreamsView _view;
    private IWoopServer _woop;

    public StreamsController(IStreamsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }



}
