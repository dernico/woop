package com.client.woop.woop.activitys;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;
import com.client.woop.woop.R;
import com.client.woop.woop.data.ClientDataStorage;
import com.client.woop.woop.data.DeviceData;
import com.client.woop.woop.data.GoogleData;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.NavigationFragment;
import com.client.woop.woop.models.Person;
import com.client.woop.woop.navigation.INavigation;
import com.client.woop.woop.navigation.Navigation;
import com.client.woop.woop.web.ImageDownloader;


public class BaseActivity extends AppCompatActivity
        implements GoogleData.GoogleConnectedListener
{
    private String TAG;

    private INavigation _navigator;
    private IWoopServer _woop;

    protected static ILogger _logger = new Logger();
    protected Menu _menu;
    protected GoogleData _google;
    private static Drawable _icon;


    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        TAG = this.getClass().getSimpleName();

        _logger.debug(TAG, "onCreate was called.");

        _navigator = new Navigation(this);

        _google = new GoogleData(this, this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        _woop = WoopServer.singelton(new ClientDataStorage(prefs), new DeviceData());

    }

   /* public void setNavigationFragment(){

        _navigationFragment = (NavigationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_fragment);

        _navigationFragment.setUp(
                R.id.navigation_fragment,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }*/

    public INavigation navigation(){
        return _navigator;
    }

    public IWoopServer woopServer(){
        return _woop;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        _menu = menu;
        this.setGoogleImageIcon(_google.getPerson());
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

    private void setGoogleImageIcon(Person person){
        _logger.info(TAG, "Setting Menu Icon with Profil Image");

        if (person == null) {
            _logger.info(TAG, "Person was null when trying to set Menu Icon");
        };

        if(_menu == null){
            _logger.error(TAG,"Menu object was null wen trying to set Menu Icon");
        }



        if(_icon == null){

            new ImageDownloader(new ImageDownloader.ImageDownloadedListener() {
                @Override
                public void downloaded(Bitmap bitmap) {

                    MenuItem item = _menu.findItem(R.id.action_user_icon);
                    if(item != null){
                        _icon = new BitmapDrawable(getResources(),bitmap);
                        item.setIcon(_icon);
                    }
                }
            }).execute(person.getImageUrl());
        }else{

            MenuItem item = _menu.findItem(R.id.action_user_icon);
            item.setIcon(_icon);
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        _logger.info(TAG, "onConnected was not implemented by the child.");
    }

}
