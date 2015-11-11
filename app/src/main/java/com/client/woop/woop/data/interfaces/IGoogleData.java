package com.client.woop.woop.data.interfaces;

import com.client.woop.woop.models.PersonModel;

/**
 * Created by nico on 9/30/2015.
 */
public interface IGoogleData {
    void connect();
    boolean personInfoAvailable();
    //boolean loggedIn();

    PersonModel getPerson();
}
