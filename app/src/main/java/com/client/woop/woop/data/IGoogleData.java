package com.client.woop.woop.data;

import com.client.woop.woop.models.Person;

/**
 * Created by nico on 9/30/2015.
 */
public interface IGoogleData {
    void connect();
    void tryConnect();
    //boolean loggedIn();

    Person getPerson();
}
