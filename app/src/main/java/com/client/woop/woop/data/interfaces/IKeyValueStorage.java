package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.KeyValueStoreDB;
import com.client.woop.woop.models.KeyValueModel;

public interface IKeyValueStorage {

    void getString(String key, KeyValueStoreDB.IKeyValueStoreCallback callback);

    void putString(String key, String value, KeyValueStoreDB.IKeyValueStoreCallback callback);

    void removeKey(String key, final KeyValueStoreDB.IKeyValueStoreCallback callback);
}
