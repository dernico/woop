package com.client.woop.woop.controller;

import com.client.woop.woop.activitys.IMainView;
import com.client.woop.woop.data.WoopServer;


/**
 * Created by nico on 9/30/2015.
 */
public class MainController {

    private IMainView _view;

    public MainController(IMainView view){
        _view = view;
        setPersonInfo();
        findWoopServer();
    }

    private void setPersonInfo(){
        _view.setPersonInfo(_view.getGoogleData().getPerson());
    }

    public void findWoopServer(){
        _view.showProgressBar();
        WoopServer.singelton().findService(new WoopServer.WoopServerListener() {
            @Override
            public void serviceFound() {
                _view.hideProgressBar();
                loadStreamList();
            }
        });
    }

    private void loadStreamList(){

    }

}
