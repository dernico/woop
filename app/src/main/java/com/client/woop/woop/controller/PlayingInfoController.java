package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IPlayingInfoView;
import com.client.woop.woop.models.PlayingInfoModel;

/**
 * Created by nico on 11/2/2015.
 */
public class PlayingInfoController implements WoopServer.WoopInfoChanged{

    private IPlayingInfoView _view;

    public PlayingInfoController(IPlayingInfoView view, IWoopServer woop){
        _view = view;
        woop.subscribePlayingInfoChanged(this);
    }

    @Override
    public void infoChanged(PlayingInfoModel info) {
        _view.setPlayingInfo(info);
    }
}
