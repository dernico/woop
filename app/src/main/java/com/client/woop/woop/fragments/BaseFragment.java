package com.client.woop.woop.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.os.Bundle;

import com.client.woop.woop.activitys.BaseActivity;
import com.client.woop.woop.data.ClientDataStorage;
import com.client.woop.woop.data.IClientDataStorage;
import com.client.woop.woop.navigation.INavigation;


public class BaseFragment extends Fragment {

    protected INavigation _navigation;
    protected IClientDataStorage _storage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _navigation = ((BaseActivity) getActivity()).navigation();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        _storage = new ClientDataStorage(prefs);
    }
}
