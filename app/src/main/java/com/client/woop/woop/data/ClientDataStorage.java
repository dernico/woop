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
    public void getString(String key, KeyValueStoreDB.IKeyValueStoreCallback callback) {
        String value = _sharedPreferences.getString(key, null);
        KeyValueModel model = new KeyValueModel();
        model.id = -1;
        model.value = value;
        model.key = key;
        callback.done(model);
    }

    @Override
    public void putString(String key, String value, KeyValueStoreDB.IKeyValueStoreCallback callback) {
        _sharedPreferences
                .edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public void removeKey(String key, KeyValueStoreDB.IKeyValueStoreCallback callback) {
        _sharedPreferences.edit().remove(key);
    }
}
