package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IMyMusicView;
import com.client.woop.woop.models.MyMusicModel;

import java.util.List;

public class MyMusicController {

    private IMyMusicView _view;
    private IWoopServer _woop;
    private List<MyMusicModel> _mymusic;

    public MyMusicController(IMyMusicView view, IWoopServer woop){
        _view = view;
        _woop = woop;
        loadMyMusic();
    }

    private void loadMyMusic(){
        _woop.getMyMusic(new WoopServer.WoopDataReceived<List<MyMusicModel>>() {
            @Override
            public void dataReceived(List<MyMusicModel> result) {
                _mymusic = result;
                _view.setListData(_mymusic);
            }

            @Override
            public void errorReceived(Exception ex) {
                //Todo: handle error properly
            }
        });
    }

    public void play(int position){
        MyMusicModel model = _mymusic.get(position);
        _woop.play(model, null);
    }

}
