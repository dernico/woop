package com.client.woop.woop.activitys;

import com.client.woop.woop.data.IGoogleData;
import com.client.woop.woop.models.Person;

/**
 * Created by nico on 9/30/2015.
 */
public interface IMainView {

    IGoogleData getGoogleData();
    void setPersonInfo(Person person);
    void showProgressBar();
    void hideProgressBar();

}
