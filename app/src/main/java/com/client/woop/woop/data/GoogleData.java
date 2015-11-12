package com.client.woop.woop.data;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.data.interfaces.IGoogleData;
import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.models.KeyValueModel;
import com.client.woop.woop.models.PersonModel;
import com.client.woop.woop.web.ImageDownloader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;

public class GoogleData implements IGoogleData,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private static String TAG = GoogleData.class.getSimpleName();

    public interface GoogleConnectedListener{
        void onConnected(Bundle bundle);
    }

    public interface PersonInfoAvailableCallback{
        void isavailable(boolean available);
    }

    public static final String PERSON_STORAGE_KEY = "GOOGLE_PERSON_JSON";

    /* Request code used to invoke sign in user interactions. */
    protected static final int RC_SIGN_IN = 0;
    public static final int REQUEST_CODE_SIGN_IN = 2;

    protected boolean mShouldResolve = false;
    protected GoogleApiClient _googleApi;
    protected ILogger _logger;

    protected Activity _activity;

    private static PersonModel _currentPerson;
    private GoogleConnectedListener _connectedListener;
    private IKeyValueStorage _storage;

    public GoogleData(Activity activity, IKeyValueStorage storage, GoogleData.GoogleConnectedListener listener){
        if(listener == null){
            throw new NullPointerException("GoogleData.GoogleConnectedListener should not be empty! Better implement it");
        }
        _logger = new Logger();
        _activity = activity;
        _connectedListener = listener;
        _storage = storage;


        _googleApi = new GoogleApiClient.Builder(_activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

    }

    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {

        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.

        _logger.debug(TAG, "onConnectionFailed:" + connectionResult);

        if(mShouldResolve && connectionResult.getErrorCode() == ConnectionResult.SIGN_IN_REQUIRED){
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(_activity, RC_SIGN_IN);
                } catch (IntentSender.SendIntentException e) {
                    _logger.error(TAG, "Could not resolve ConnectionResult." + e.toString());
                    _googleApi.connect();
                }
            } else {
                // Could not resolve the connection result
                _logger.error(TAG, "Could not resolve the connection result");
            }
        }

    }

    @Override
    public void onConnected(Bundle bundle) {

        this.loadPerson(null);
        _connectedListener.onConnected(bundle);
        _googleApi.disconnect();

    }

    @Override
    public void onConnectionSuspended(int i) {
        _logger.info(TAG, "onConnectionSuspended was called.");
    }


    public void connect(){
        mShouldResolve = true;
        _googleApi.connect();
    }

    @Override
    public void personInfoAvailable(final PersonInfoAvailableCallback callback){
        _storage.getString(PERSON_STORAGE_KEY, new KeyValueStoreDB.IKeyValueStoreCallback() {
            @Override
            public void done(KeyValueModel result) {
                if(result == null){
                    _googleApi.connect();
                }else {
                    try {
                        _currentPerson = PersonModel.createFromJSON(result.value);
                        callback.isavailable(true);
                        return;

                    } catch (JSONException e) {
                        _logger.error(TAG, "Could not parse the saved person infos from sqlite: " + e.toString());
                        _googleApi.connect();
                    }
                }
                callback.isavailable(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            if(!_googleApi.isConnected() && !_googleApi.isConnecting()){
                _googleApi.connect();
            }
        }
    }


    public PersonModel getPerson(){
        return _currentPerson;
    }

    @Override
    public void resetPerson(final KeyValueStoreDB.IKeyValueStoreCallback callback) {
        _storage.removeKey(PERSON_STORAGE_KEY, new KeyValueStoreDB.IKeyValueStoreCallback() {
            @Override
            public void done(KeyValueModel result) {
                _currentPerson = null;
                callback.done(null);
            }
        });
    }


    private void loadPerson(final KeyValueStoreDB.IKeyValueStoreCallback callback){

        if(_currentPerson == null){
            _currentPerson = loadPersonInfos();

                new ImageDownloader(new ImageDownloader.ImageDownloadedListener() {
                    @Override
                    public void downloaded(Bitmap bitmap) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        _currentPerson.setImage(stream.toByteArray());
                        try {
                            String jsonString = _currentPerson.toJSON().toString();
                            _storage.putString(PERSON_STORAGE_KEY, jsonString, new KeyValueStoreDB.IKeyValueStoreCallback() {
                                @Override
                                public void done(KeyValueModel result) {
                                    if(callback != null){
                                        callback.done(result);
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            _logger.error(TAG, "Could not save Personinfos due to a JSON Exception: " +e.toString());
                        }
                    }
                }).execute(_currentPerson.getImageUrl());


        }
    }

    private PersonModel loadPersonInfos(){

        PersonModel model = new PersonModel();

        com.google.android.gms.plus.model.people.Person gP = Plus.PeopleApi.getCurrentPerson(_googleApi);

        String accountName = Plus.AccountApi.getAccountName(_googleApi);

        model.setId(gP.getId());
        model.setName(gP.getName().getGivenName());
        model.setSurname(gP.getName().getFamilyName());
        model.setDisplayName(gP.getDisplayName());
        model.setAccountName(accountName);


        model.setImageUrl(gP.getImage().getUrl());
        return model;
    }

}
