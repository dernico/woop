package com.client.woop.woop.controller;


import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.extensions.EightTracksList;
import com.client.woop.woop.fragments.interfaces.IEightTracksView;
import com.client.woop.woop.models.EightTracksModel;
import com.client.woop.woop.models.PlayingInfoModel;

public class EightTracksController {

    IEightTracksView _view;
    IWoopServer _woop;
    EightTracksList _currentResult;

    public EightTracksController(IEightTracksView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }

    public void searchPopular(){
        _view.showLoading();
        String tag = "all:popular";
        _woop.eightTracksTag(tag, new WoopServer.WoopDataReceived<EightTracksList>() {
            @Override
            public void dataReceived(EightTracksList result) {
                _currentResult = result;
                _view.setSerachResults(result);
                _view.hideLoading();
            }

            @Override
            public void errorReceived(Exception ex) {
                //TODO: handle properly
            }
        });
    }

    public void play(int position){
        EightTracksModel model = _currentResult.get(position);
        _woop.eightTracksPlay(model, new WoopServer.WoopDataReceived<PlayingInfoModel>() {
            @Override
            public void dataReceived(PlayingInfoModel result) {
                //Todo: I don't care for the result at this point ;)
            }

            @Override
            public void errorReceived(Exception ex) {
                //TODO: proper error handling
            }
        });
    }
}
