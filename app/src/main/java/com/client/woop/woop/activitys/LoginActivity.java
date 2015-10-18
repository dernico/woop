package com.client.woop.woop.activitys;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.client.woop.woop.R;
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
        navigation().navigateMain();
    }
}
