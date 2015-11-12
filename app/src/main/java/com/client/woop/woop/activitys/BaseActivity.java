package com.client.woop.woop.activitys;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.R;
import com.client.woop.woop.data.DeviceData;
import com.client.woop.woop.data.GoogleData;
import com.client.woop.woop.data.KeyValueStorage;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IGoogleData;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.models.PersonModel;
import com.client.woop.woop.navigation.INavigation;
import com.client.woop.woop.navigation.Navigation;
import com.client.woop.woop.web.ImageDownloader;


public class BaseActivity extends AppCompatActivity
        implements GoogleData.GoogleConnectedListener,
        WoopServer.IServerAvailable
{
    private String TAG;

    private INavigation _navigator;
    private ProgressDialog _progressDialog;

    protected static ILogger _logger = new Logger();
    protected Menu _menu;
    private GoogleData _google;
    private static Drawable _icon;


    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        TAG = this.getClass().getSimpleName();

        _logger.debug(TAG, "onCreate was called.");

        _navigator = new Navigation(this);


        _progressDialog = new ProgressDialog(this);

    }

    public INavigation navigation(){
        return _navigator;
    }

    public IGoogleData googleData() {
        // GoogleData and api will only be create for every Activity once.
        // If it is null e.g when rotation change occurs it will create it again
        // for the Activity.
        if(_google == null){
            _google = new GoogleData(this, new KeyValueStorage(this), this);
        }
        return _google;
    }

    public IWoopServer woopServer(){
        WoopServer woop = WoopServer.singelton(new KeyValueStorage(this), new DeviceData());
        woop.setServerAvailableCallback(this);
        return woop;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        _menu = menu;
        this.setGoogleImageIcon(googleData().getPerson());
        this.setServerOnlineStatus(woopServer().isServerOnline());
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        _logger.debug(TAG, "onStart was called.");
    }

    @Override
    public void onStop() {
        super.onStop();
        _logger.debug(TAG, "onStop was called.");
    }
    @Override
    public void onResume(){
        super.onResume();
        _logger.debug(TAG, "onResume was called.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        _logger.debug(TAG, "onDestroy was called.");
    }

    private void setServerOnlineStatus(boolean status){
        if (_menu == null) return;

        MenuItem item = _menu.findItem(R.id.action_server_status);
        if(item != null){
            if(status){
                item.setIcon(ContextCompat.getDrawable(this, R.mipmap.online));
            }else{
                item.setIcon(ContextCompat.getDrawable(this, R.mipmap.offline));
            }
        }
    }

    private void setGoogleImageIcon(PersonModel person){
        _logger.info(TAG, "Setting Menu Icon with Profil Image");

        if (person == null) {
            _logger.info(TAG, "Person was null when trying to set Menu Icon");
        };

        if(_menu == null){
            _logger.error(TAG,"Menu object was null wen trying to set Menu Icon");
            return;
        }

        MenuItem item = _menu.findItem(R.id.action_user_icon);
        if (item == null){
            _logger.info(TAG,"Could not find R.id.action_user_icon, so I don't set it");
            return;
        }

        if(_icon == null){
            Bitmap bm = BitmapFactory.decodeByteArray(person.getImage(), 0, person.getImage().length);
            _icon = new BitmapDrawable(getResources(), bm);
            item.setIcon(_icon);
        }else{

            item.setIcon(_icon);

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        _logger.info(TAG, "onConnected was not implemented by the child.");
    }

    public void showProgessbar(String title, String message){
        _progressDialog.setTitle(title);
        _progressDialog.setMessage(message);
        _progressDialog.show();
    }

    public void hideProgressBar() {
        _progressDialog.dismiss();
    }


    @Override
    public void serverAvailable(boolean available) {
        setServerOnlineStatus(available);
    }
}
