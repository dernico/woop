package com.client.woop.woop.navigation;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.client.woop.woop.R;
import com.client.woop.woop.activitys.LoginActivity;
import com.client.woop.woop.activitys.MainActivity;
import com.client.woop.woop.fragments.locals.ListeFragment;
import com.client.woop.woop.fragments.settings.SettingsFragment;
import com.client.woop.woop.fragments.streams.StreamsFragment;
import com.client.woop.woop.fragments.youtube.YouTubeFragment;

public class Navigation implements INavigation {

    private AppCompatActivity _context;

    public Navigation(AppCompatActivity context){
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
    public void goBack(){
        _context.getSupportFragmentManager().popBackStack();
    }

    @Override
    public void navigateStartFragment() {
        navigateFragmentList();
    }

    private void changeFragment(Fragment fragment){
        //TODO: Make sure MainActivity is loaded
        FragmentManager fragmentManager = _context.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getTag())
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
