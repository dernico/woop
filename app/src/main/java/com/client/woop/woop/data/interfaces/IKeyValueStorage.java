package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.KeyValueStoreDB;

/**
 * Created by nico on 10/13/2015.
 */
public interface IKeyValueStorage {

    void getString(String key, KeyValueStoreDB.IKeyValueStoreCallback callback);
    //int getInt(String key);

    void putString(String key, String value, KeyValueStoreDB.IKeyValueStoreCallback callback);
    //void putInt(String key, int value);

    void removeKey(String key, KeyValueStoreDB.IKeyValueStoreCallback callback);
}
