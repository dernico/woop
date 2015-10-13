package com.client.woop.woop.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;;
import android.view.Menu;
import android.view.MenuItem;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.MainController;
import com.client.woop.woop.data.IGoogleData;
import com.client.woop.woop.models.Person;

public class MainActivity extends BaseActivity implements IMainView{


    private ProgressDialog _progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _progressDialog = new ProgressDialog(this);

        setNavigation();

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
    public void showProgressBar() {
        _progressDialog.setTitle("Searching Woop Server");
        _progressDialog.setMessage("Wait while searching...");
        _progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        _progressDialog.dismiss();
    }
}
