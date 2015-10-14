package com.client.woop.woop.data.interfaces;

/**
 * Created by nico on 10/13/2015.
 */
public interface IClientDataStorage {

    String getString(String key);
    int getInt(String key);

    void putString(String key, String value);
    void putInt(String key, int value);

    void removeKey(String key);
}
