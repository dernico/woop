package com.client.woop.woop.activitys;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.MainController;
import com.client.woop.woop.data.IGoogleData;
import com.client.woop.woop.fragments.NavigationFragment;
import com.client.woop.woop.models.Person;

public class MainActivity extends BaseActivity implements IMainView, NavigationFragment.OnFragmentInteractionListener{

    private NavigationFragment _navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _navigationFragment = (NavigationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_fragment);

        _navigationFragment.setUp(
                R.id.navigation_fragment,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        new MainController(this);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public IGoogleData getGoogleData() {
        return _google;
    }

    @Override
    public void setPersonInfo(Person person) {
    }

    @Override
    public void onNavigationItemSelected(android.support.v4.app.Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
