package com.client.woop.woop.navigation;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.app.FragmentManager;

import com.client.woop.woop.R;
import com.client.woop.woop.activitys.LoginActivity;
import com.client.woop.woop.activitys.MainActivity;
import com.client.woop.woop.fragments.ListeFragment;
import com.client.woop.woop.fragments.SettingsFragment;
import com.client.woop.woop.fragments.StreamsFragment;
import com.client.woop.woop.fragments.YouTubeFragment;

public class Navigation implements INavigation {

    private Activity _context;

    public Navigation(Activity context){
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

    @Override
    public void navigateStartFragment() {
        navigateFragmentList();
    }

    private void changeFragment(Fragment fragment){
        //TODO: Make sure MainActivity is loaded
        FragmentManager fragmentManager = _context.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void navigateFragmentList() {
        changeFragment(ListeFragment.newInstance());
    }

    @Override
    public void navigateFragmentStreams() {
        changeFragment(StreamsFragment.newInstance());
    }

    @Override
    public void navigateFragmentYouTube() {
        changeFragment(YouTubeFragment.newInstance());
    }


    @Override
    public void navigateFragment8Tracks() {
        //TODO: changeFragment(YouTubeFragment.newInstance());
    }

    @Override
    public void navigateFragmentSettings() {
        changeFragment(SettingsFragment.newInstance());
    }

}
