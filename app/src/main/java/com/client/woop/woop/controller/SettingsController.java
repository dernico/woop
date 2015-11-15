package com.client.woop.woop.controller;

import com.client.woop.woop.data.KeyValueStoreDB;
import com.client.woop.woop.data.interfaces.IGoogleData;
import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.fragments.interfaces.ISettingsView;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.models.KeyValueModel;
import com.client.woop.woop.navigation.INavigation;


public class SettingsController implements WoopServer.WoopServerListener {

    private ISettingsView _view;
    private IWoopServer _woop;
    private INavigation _navigation;
    private IGoogleData _google;
    private int _notFoundCounter;

    public SettingsController(ISettingsView view,
                              IWoopServer server,
                              INavigation navigation,
                              IGoogleData google){
        _view = view;
        _woop = server;
        _navigation = navigation;
        _google = google;
        init();
    }

    private void init(){
        String serviceAdress = _woop.getServiceAddress();
        _view.setServiceAddress(serviceAdress);
        _view.setPersonInfo(_google.getPerson());
    }

    public void resetUserData(){
        _google.resetPerson(new KeyValueStoreDB.IKeyValueStoreCallback() {
            @Override
            public void done(KeyValueModel result) {
                _navigation.navigateLogin();
            }
        });
    }

    public void searchServer(){
        _notFoundCounter = 0;
        _view.showProgressBar("Busy finding woop server");
        _woop.findService(this);
    }

    @Override
    public void serviceFound() {
        _view.hideProgressBar();
        _navigation.navigateMain();
    }

    @Override
    public void serviceNotFound(String address) {
        _notFoundCounter += 1;
        _view.showProgressBar("Attempt " + _notFoundCounter + " of 255 :)");
    }
}
