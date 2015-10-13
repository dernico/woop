package com.client.woop.woop.controller;

import com.client.woop.woop.activitys.ISettingsView;
import com.client.woop.woop.data.IWoopServer;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.navigation.INavigation;
import com.client.woop.woop.navigation.Navigation;


public class SettingsController {

    private ISettingsView _view;
    private IWoopServer _woop;
    private INavigation _navigation;

    public SettingsController(ISettingsView view, WoopServer server, INavigation navigation){
        _view = view;
        _woop = server;
        _navigation = navigation;
    }

    public void searchServer(){
        _view.showProgressBar();
        _woop.findService(new WoopServer.WoopServerListener() {
            @Override
            public void serviceFound() {
                _view.hideProgressBar();
                _navigation.navigateMain();
            }
        });
    }

    public void resetServer(){
        _woop.resetService();
        searchServer();
    }


}
