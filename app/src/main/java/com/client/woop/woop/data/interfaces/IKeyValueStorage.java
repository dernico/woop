package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.KeyValueStoreDB;
import com.client.woop.woop.models.KeyValueModel;

/**
 * Created by nico on 10/13/2015.
 */
public interface IKeyValueStorage {

    KeyValueModel getString(String key);
    //int getInt(String key);

    void putString(String key, String value);
    //void putInt(String key, int value);

    void removeKey(String key);
}
