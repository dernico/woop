package com.client.woop.woop.controller;

import com.client.woop.woop.activitys.interfaces.ISavedStreamsView;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.models.PlayingInfo;
import com.client.woop.woop.models.StreamModel;

import java.util.List;

/**
 * Created by nico on 10/22/2015.
 */
public class SavedStreamsController {

    private ISavedStreamsView _view;
    private IWoopServer _woop;
    private List<StreamModel> _savedStreams;

    public SavedStreamsController(ISavedStreamsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }


    public void loadStreams(){

        _woop.getSavedStreams(new WoopServer.WoopDataReceived<List<StreamModel>>() {
            @Override
            public void dataReceived(List<StreamModel> result) {
                _savedStreams = result;
                _view.setStreams(result);
            }

            @Override
            public void errorReceived(Exception ex) {
                //TODO: handle exception properly
            }
        });
    }

    public void playStream(int position){
        StreamModel model = _savedStreams.get(position);
        _woop.playSavedStream(model, new WoopServer.WoopDataReceived<PlayingInfo>() {
            @Override
            public void dataReceived(PlayingInfo result) {
                //TODO: do something with the result
            }

            @Override
            public void errorReceived(Exception ex) {
                //TODO: handle exception properly
            }
        });
    }
}
