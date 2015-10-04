package com.client.woop.woop.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.client.woop.woop.R;
import com.client.woop.woop.data.GoogleData;
import com.client.woop.woop.data.IGoogleData;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SignInButton loginButton = (SignInButton)findViewById(R.id.login_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _google.connect();
            }
        });

        _google.tryConnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        _navigator.navigateMain();
    }
/*
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
    }*/
}
