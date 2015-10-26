package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IPlayControlsView;
import com.client.woop.woop.models.PlayingInfo;


public class PlayControlsController implements WoopServer.WoopDataReceived<PlayingInfo>{

    IPlayControlsView _view;
    IWoopServer _woop;

    public PlayControlsController(IPlayControlsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
        this.setPlayState(_woop.currentPlayingInfo());
    }

    public void play_pause(){
        if(_woop.isPlaying()){
            _woop.pause(this);
        }
        else{
            _woop.play(this);
        }
    }

    public void prev(){
        _woop.prev(this);
    }

    public void next(){
        _woop.next(this);
    }

    public void shuffle(){
        _woop.shuffle(this);
    }

    @Override
    public void dataReceived(PlayingInfo result) {
        this.setPlayState(result);
        _view.setVolume("" + result.Volume);
    }

    @Override
    public void errorReceived(Exception ex) {
        //TODO: Handle Error properly
    }

    private void setPlayState(PlayingInfo info){
        if(info == null){ return; }

        if(info.IsPlaying){
            _view.setPlaying();
        }
        else{
            _view.setPause();
        }
    }
}
