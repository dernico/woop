package com.client.woop.woop.data.interfaces;

import android.content.Intent;

import com.client.woop.woop.data.GoogleData;
import com.client.woop.woop.models.PersonModel;


public interface IGoogleData {
    void connect();
    void personInfoAvailable(GoogleData.PersonInfoAvailableCallback callback);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    PersonModel getPerson();
}
