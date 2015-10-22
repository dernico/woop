package com.client.woop.woop.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.client.woop.woop.activitys.BaseActivity;
import com.client.woop.woop.data.ClientDataStorage;
import com.client.woop.woop.data.interfaces.IClientDataStorage;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.navigation.INavigation;


public class BaseFragment extends Fragment {

    private INavigation _navigation;
    private IClientDataStorage _storage;
    private IWoopServer _woop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity parent = ((BaseActivity) getActivity());
        _navigation = parent.navigation();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        _storage = new ClientDataStorage(prefs);
        _woop = parent.woopServer();
    }

    public IWoopServer woopServer(){
        return _woop;
    }

    public INavigation navigation(){return _navigation;}

    public IClientDataStorage storage(){return _storage;}
}
