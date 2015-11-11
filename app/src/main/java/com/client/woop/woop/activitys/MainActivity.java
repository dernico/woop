package com.client.woop.woop.activitys;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.client.woop.woop.R;
import com.client.woop.woop.activitys.interfaces.IMainView;
import com.client.woop.woop.controller.MainController;
import com.client.woop.woop.fragments.NavigationFragment;

public class MainActivity extends BaseActivity implements IMainView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationFragment();
        new MainController(this, woopServer(), navigation());
    }

    private void setNavigationFragment() {
        NavigationFragment navigationFragment = (NavigationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_fragment);

        navigationFragment.setUp(
                R.id.navigation_fragment,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    public void onBackPressed(){
        // bigger 1 because the first one is the start fragment
        if (getFragmentManager().getBackStackEntryCount() > 1 ){
            navigation().goBack();
        } else {
            super.onBackPressed();
        }
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
