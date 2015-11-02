package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IServerMusicView;
import com.client.woop.woop.models.ServerMusicModel;
import com.client.woop.woop.models.PlayingInfoModel;

import java.util.List;

public class ServerMusicController {

    private IServerMusicView _view;
    private IWoopServer _woop;
    private List<ServerMusicModel> _mymusic;

    public ServerMusicController(IServerMusicView view, IWoopServer woop){
        _view = view;
        _woop = woop;
        loadMyMusic();
    }

    private void loadMyMusic(){
        _woop.getMyMusic(new WoopServer.WoopDataReceived<List<ServerMusicModel>>() {
            @Override
            public void dataReceived(List<ServerMusicModel> result) {
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

        ServerMusicModel model = _mymusic.get(position);
        _woop.play(model, null);
    }

}
