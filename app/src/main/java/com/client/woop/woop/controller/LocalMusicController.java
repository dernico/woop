package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.ILocalMusicData;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.ILocalMusicView;
import com.client.woop.woop.models.LocalMusicModel;
import com.client.woop.woop.models.PlayingInfoModel;

import java.util.List;

public class LocalMusicController {

    private ILocalMusicView _view;
    private ILocalMusicData _localMusic;
    private IWoopServer _woop;

    public LocalMusicController(ILocalMusicView view, ILocalMusicData localMusic, IWoopServer woop){
        _view = view;
        _localMusic = localMusic;
        _woop = woop;
    }

    public void loadLocalMusic(){
        List<LocalMusicModel> files = _localMusic.getLocalMusic();
        _view.setLocalMusic(files);
    }

    public void playTrackOnServer(int position){
        _view.showProgress("Prepare Playing", "Preparing the track to get played ...");

        LocalMusicModel model = _localMusic.getLocalMusic().get(position);
        _woop.play(model, new WoopServer.WoopDataReceived<PlayingInfoModel>() {
            @Override
            public void dataReceived(PlayingInfoModel result) {
                _view.hideProgress();
            }

            @Override
            public void errorReceived(Exception ex) {
                _view.hideProgress();
            }
        });
    }
}
