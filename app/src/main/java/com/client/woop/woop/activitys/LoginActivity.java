package com.client.woop.woop.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.client.woop.woop.R;
import com.client.woop.woop.data.GoogleData;
import com.client.woop.woop.data.KeyValueStorage;
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

        _google.personInfoAvailable(new GoogleData.PersonInfoAvailableCallback() {
            @Override
            public void isavailable(boolean available) {
                if (available) {
                    navigation().navigateMain();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        _google.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        navigation().navigateMain();
    }
}
