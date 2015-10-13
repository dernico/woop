package com.client.woop.woop.activitys;

import android.content.SharedPreferences;
import android.os.Bundle;;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.MainController;
import com.client.woop.woop.data.ClientDataStorage;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.fragments.NavigationFragment;

public class MainActivity extends BaseActivity implements IMainView{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationFragment();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        new MainController(this, WoopServer.singelton(new ClientDataStorage(prefs)), navigation());
    }

    private void setNavigationFragment() {
        NavigationFragment navigationFragment = (NavigationFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_fragment);

        navigationFragment.setUp(
                R.id.navigation_fragment,
                (DrawerLayout) findViewById(R.id.drawer_layout));
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

}
