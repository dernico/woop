package com.client.woop.woop.navigation;


import android.content.Context;
import android.content.Intent;

import com.client.woop.woop.activitys.LoginActivity;
import com.client.woop.woop.activitys.MainActivity;

public class Navigation implements INavigation {

    private Context _context;

    public Navigation(Context context){
        _context = context;
    }

    @Override
    public void navigateMain() {
        _context.startActivity(new Intent(_context, MainActivity.class));
    }

    @Override
    public void navigateLogin() {
        _context.startActivity(new Intent(_context, LoginActivity.class));
    }

}
