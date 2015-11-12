package com.client.woop.woop.fragments.interfaces;


import com.client.woop.woop.models.PersonModel;

public interface ISettingsView {

    void showProgressBar(String message);
    void hideProgressBar();

    void setServiceAddress(String address);
    void setPersonInfo(PersonModel model);
}
