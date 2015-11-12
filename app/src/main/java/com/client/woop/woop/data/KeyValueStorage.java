package com.client.woop.woop.data;


import android.content.Context;

import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.models.KeyValueModel;

public class KeyValueStorage implements IKeyValueStorage {

    private KeyValueStoreDB _db;

    public KeyValueStorage(Context context){
        _db = new KeyValueStoreDB(context);
    }

    @Override
    public void getString(String key, KeyValueStoreDB.IKeyValueStoreCallback callback) {
        _db.getKeyValue(key, callback);
    }

    @Override
    public void putString(String key, String value, KeyValueStoreDB.IKeyValueStoreCallback callback) {
        _db.insertKeyValue(key, value, callback);
    }

    @Override
    public void removeKey(String key, final KeyValueStoreDB.IKeyValueStoreCallback callback) {
        _db.removeKey(key, callback);
    }
}
