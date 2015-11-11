package com.client.woop.woop.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.client.woop.woop.activitys.BaseActivity;
import com.client.woop.woop.data.KeyValueStorage;
import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.navigation.INavigation;


public class BaseFragment extends Fragment {

    private INavigation _navigation;
    private IKeyValueStorage _storage;
    private IWoopServer _woop;
    private BaseActivity _parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _parentActivity = ((BaseActivity) getActivity());
        _navigation = _parentActivity.navigation();

        _storage = new KeyValueStorage(getContext());
        _woop = _parentActivity.woopServer();
    }

    public IWoopServer woopServer(){
        return _woop;
    }

    public INavigation navigation(){return _navigation;}

    public IKeyValueStorage storage(){return _storage;}

    public void showProgressbar(String title, String message){
        _parentActivity.showProgessbar(title, message);
    }

    public void hideProgressbar(){
        _parentActivity.hideProgressBar();
    }
}
