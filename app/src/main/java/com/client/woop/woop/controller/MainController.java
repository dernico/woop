package com.client.woop.woop.controller;

import com.client.woop.woop.activitys.interfaces.IMainView;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.navigation.INavigation;


public class MainController {

    private IMainView _view;
    private INavigation _navigaiton;
    private IWoopServer _woop;

    public MainController(IMainView view, IWoopServer woop, INavigation navigation){
        _view = view;
        _navigaiton = navigation;
        _woop = woop;
        checkServerStatus();
    }

    private void checkServerStatus(){
        if(!_woop.isServerOnline()){
            _navigaiton.navigateFragmentSettings();
        }
    }
}
