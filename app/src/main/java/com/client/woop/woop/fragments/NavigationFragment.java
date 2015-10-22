package com.client.woop.woop.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.client.woop.woop.R;
import com.client.woop.woop.activitys.BaseActivity;
import com.client.woop.woop.navigation.INavigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BaseFragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private int _currentSelectedPosition = 0;
    private boolean _fromSavedInstanceState;
    private boolean _userLearnedDrawer;


    private ActionBarDrawerToggle _drawerToggle;
    private DrawerLayout _drawerLayout;
    private ListView _drawerListView;
    private View _fragmentContainerView;


    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        _userLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            _currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            _fromSavedInstanceState = true;
        }

        selectItem(_currentSelectedPosition);

        //setUp();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _drawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation, container, false);

        _drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        _drawerListView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.navigation_liste),
                        getString(R.string.navigation_streams),
                        getString(R.string.navigation_8_tracks),
                        getString(R.string.navigation_youtube),
                        getString(R.string.navigation_settings)
                }));
        _drawerListView.setItemChecked(_currentSelectedPosition, true);
        return _drawerListView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, _currentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        _drawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (_drawerLayout != null && isDrawerOpen()) {
            //inflater.inflate(R.menu.menu_main, menu);
            //showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setUp(int navigation_fragment, DrawerLayout drawerLayout) {

        _fragmentContainerView = getActivity().findViewById(navigation_fragment);
        _drawerLayout = drawerLayout;


        // set a custom shadow that overlays the main content when the drawer opens
        _drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        _drawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                _drawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_open,  /* "open drawer" description for accessibility */
                R.string.navigation_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu();
                //getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!_userLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    _userLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu();
                //getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!_userLearnedDrawer && !_fromSavedInstanceState) {
            _drawerLayout.openDrawer(_fragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        _drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                _drawerToggle.syncState();
            }
        });

        _drawerLayout.setDrawerListener(_drawerToggle);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void selectItem(int position) {
        _currentSelectedPosition = position;
        if (_drawerListView != null) {
            _drawerListView.setItemChecked(position, true);
        }
        if (_drawerLayout != null) {
            _drawerLayout.closeDrawer(_fragmentContainerView);
        }
        switch (position){
            case 0:
                navigation().navigateFragmentList();
                break;
            case 1:
                navigation().navigateFragmentStreams();
                break;
            case 2:
                navigation().navigateFragment8Tracks();
                break;
            case 3:
                navigation().navigateFragmentYouTube();
                break;
            case 4:
                navigation().navigateFragmentSettings();
                break;
            default:
                navigation().navigateFragmentList();
        }

    }

    public boolean isDrawerOpen() {
        return _drawerLayout != null && _drawerLayout.isDrawerOpen(_fragmentContainerView);
    }


    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }
}
