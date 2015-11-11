package com.client.woop.woop.data;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.data.interfaces.IGoogleData;
import com.client.woop.woop.data.interfaces.IKeyValueStorage;
import com.client.woop.woop.models.KeyValueModel;
import com.client.woop.woop.models.PersonModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import org.json.JSONException;

/**
 * Created by nico on 9/30/2015.
 * TODO: Maybe cache the whole google Stuff and prove a refresh button somewhere in the UI ?
 */
public class GoogleData implements IGoogleData,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private static String TAG = GoogleData.class.getSimpleName();

    public interface GoogleConnectedListener{
        void onConnected(Bundle bundle);
    }

    protected static final String PERSON_STORAGE_KEY = "GOOGLE_PERSON_JSON";

    /* Request code used to invoke sign in user interactions. */
    protected static final int RC_SIGN_IN = 0;
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
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
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
                // Could not resolve the connection result, show the user an
                // error dialog.
                // TODO: showErrorDialog(connectionResult);
                _logger.error(TAG, "Could not resolve the connection result");
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        this.loadPerson();
        _connectedListener.onConnected(bundle);
        _googleApi.disconnect();

    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    public void connect(){
        mShouldResolve = true;
        _googleApi.connect();
    }

    @Override
    public boolean personInfoAvailable(){
        KeyValueModel person = _storage.getString(PERSON_STORAGE_KEY);
        if(person == null){
            _googleApi.connect();
        }else {
            try {
                _currentPerson = PersonModel.createFromJSON(person.value);
                return true;
            } catch (JSONException e) {
                _logger.error(TAG, "Could not parse the saved person infos from sqlite: " + e.toString());
                _googleApi.connect();
            }
        }

        return false;
    }



    public PersonModel getPerson(){
        return _currentPerson;
    }


    private void loadPerson(){

        if(_currentPerson == null){
            _currentPerson = loadPersonInfos();
            try {
                _storage.putString(PERSON_STORAGE_KEY, _currentPerson.toJSON().toString());
            } catch (JSONException e) {
                _logger.error(TAG, "Could not save Personinfos due to a JSON Exception: " +e.toString());
            }

        }
    }

    private PersonModel loadPersonInfos(){

        PersonModel model = new PersonModel();

        com.google.android.gms.plus.model.people.Person gP = Plus.PeopleApi.getCurrentPerson(_googleApi);
        model.setId(gP.getId());
        model.setName(gP.getName().getGivenName());
        model.setSurname(gP.getName().getFamilyName());
        model.setDisplayName(gP.getDisplayName());

        model.setImageUrl(gP.getImage().getUrl());
        return model;
    }

}
