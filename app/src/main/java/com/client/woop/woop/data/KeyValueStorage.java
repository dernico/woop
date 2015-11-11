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
    public KeyValueModel getString(String key) {
        return _db.getKeyValue(key);
    }

    @Override
    public void putString(String key, String value) {
        _db.insertKeyValue(key, value);
    }

    @Override
    public void removeKey(String key) {

    }
}
