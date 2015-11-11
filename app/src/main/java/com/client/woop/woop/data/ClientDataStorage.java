package com.client.woop.woop.data;

import android.content.SharedPreferences;

import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.models.KeyValueModel;


public class ClientDataStorage implements IKeyValueStorage {

    private SharedPreferences _sharedPreferences;

    public ClientDataStorage(SharedPreferences sp){
        _sharedPreferences = sp;
    }

    @Override
    public KeyValueModel getString(String key) {
        String value = _sharedPreferences.getString(key, null);
        KeyValueModel model = new KeyValueModel();
        model.id = -1;
        model.value = value;
        model.key = key;
        return model;
    }

    @Override
    public void putString(String key, String value) {
        _sharedPreferences
                .edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public void removeKey(String key) {
        _sharedPreferences.edit().remove(key);
    }
}
