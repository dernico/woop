package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.data.WoopServer;

/**
 * Created by nico on 10/13/2015.
 */
public interface IWoopServer {
    boolean isServiceAdressSet();
    void findService(WoopServer.WoopServerListener listener);
    void resetService();
}
