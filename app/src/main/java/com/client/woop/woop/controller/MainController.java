package com.client.woop.woop.controller;

import com.client.woop.woop.activitys.interfaces.IMainView;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.navigation.INavigation;


public class MainController implements WoopServer.WoopServerListener {

    private IMainView _view;
    private INavigation _navigaiton;
    private IWoopServer _woop;

    public MainController(IMainView view, IWoopServer woop, INavigation navigation){
        _view = view;
        _navigaiton = navigation;
        _woop = woop;
        VerifyWoopServiceIsAvailable();
    }

    private void VerifyWoopServiceIsAvailable(){
        _woop.isServiceAdressSet(this);
    }

    @Override
    public void serviceFound() {
        // We dont need to handle it here
    }

    @Override
    public void serviceAddressSet(boolean isSet) {
        if (!isSet){
            _navigaiton.navigateFragmentSettings();
        }
    }
}
