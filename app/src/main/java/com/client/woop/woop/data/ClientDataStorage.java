package com.client.woop.woop.data;

import android.content.SharedPreferences;

import com.client.woop.woop.data.interfaces.IClientDataStorage;


public class ClientDataStorage implements IClientDataStorage {

    private SharedPreferences _sharedPreferences;

    public ClientDataStorage(SharedPreferences sp){
        _sharedPreferences = sp;
    }

    @Override
    public String getString(String key) {
        return _sharedPreferences.getString(key, null);
    }

    @Override
    public int getInt(String key) {
        return _sharedPreferences.getInt(key, -1);
    }

    @Override
    public void putString(String key, String value) {
        _sharedPreferences
                .edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public void putInt(String key, int value) {
        _sharedPreferences
                .edit()
                .putInt(key, value)
                .apply();
    }

    @Override
    public void removeKey(String key) {
        _sharedPreferences.edit().remove(key);
    }
}
