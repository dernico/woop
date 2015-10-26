package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.IPlayControlsView;
import com.client.woop.woop.models.PlayingInfo;


public class PlayControlsController implements
        WoopServer.WoopDataReceived<PlayingInfo> ,
        WoopServer.WoopInfoChanged
{

    IPlayControlsView _view;
    IWoopServer _woop;

    public PlayControlsController(IPlayControlsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
        _woop.subscribePlayingInfoChanged(this);
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

    public void volUp(){
        _woop.volumeUp(this);
    }

    public void volDown(){
        _woop.volumeDown(this);
    }

    private void setInfoStates(PlayingInfo info){
        this.setPlayState(info);
        _view.setVolume("" + info.Volume);
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

    @Override
    public void dataReceived(PlayingInfo result) {
        // Nothing needs to be done here,
        // cause the infoChanged also gets the current PlayingInfo

        //this.setInfoStates(result);
    }

    @Override
    public void errorReceived(Exception ex) {
        //TODO: Handle Error properly
    }

    @Override
    public void infoChanged(PlayingInfo info) {
        this.setInfoStates(info);
    }

}
