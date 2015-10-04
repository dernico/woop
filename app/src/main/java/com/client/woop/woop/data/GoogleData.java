package com.client.woop.woop.data;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.models.Person;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

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


    /* Request code used to invoke sign in user interactions. */
    protected static final int RC_SIGN_IN = 0;


    /* Should we automatically resolve ConnectionResults when possible? */
    protected boolean mShouldResolve = false;
    protected GoogleApiClient _googleApi;
    protected ILogger _logger;

    protected Activity _activity;

    private static Person _currentPerson;
    private GoogleConnectedListener _connectedListener;

    public GoogleData(Activity activity, GoogleData.GoogleConnectedListener listener){
        if(listener == null){
            throw new NullPointerException("GoogleData.GoogleConnectedListener should not be empty! Better implement it");
        }
        _logger = new Logger();
        _activity = activity;
        _connectedListener = listener;

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
                    //mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    _logger.error(TAG, "Could not resolve ConnectionResult." + e.toString());
                    //mIsResolving = false;
                    _googleApi.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                // TODO: showErrorDialog(connectionResult);
                _logger.error(TAG, "Could not resolve the connection result");
            }
        }


       /* if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(tag, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    _googleApi.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                // TODO: showErrorDialog(connectionResult);
                Log.e(tag, "Could not resolve the connection result");
            }
        } else {
            // Show the signed-out UI
            // TODO: showSignedOutUI();
            Log.e(tag, "Signed out");
        }*/
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


    public void tryConnect(){
        _googleApi.connect();
    }



    public Person getPerson(){

        return _currentPerson;
    }


    private void loadPerson(){

        if(_currentPerson == null){
            _currentPerson = new Person();

            com.google.android.gms.plus.model.people.Person gP = Plus.PeopleApi.getCurrentPerson(_googleApi);
            _currentPerson.setId(gP.getId());
            _currentPerson.setName(gP.getName().getGivenName());
            _currentPerson.setSurname(gP.getName().getFamilyName());
            _currentPerson.setDisplayName(gP.getDisplayName());

            _currentPerson.setImageUrl(gP.getImage().getUrl());
        }
    }

}
