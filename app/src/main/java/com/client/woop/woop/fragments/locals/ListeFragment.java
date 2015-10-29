package com.client.woop.woop.fragments.locals;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.StreamsController;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.streams.SavedStreamsFragment;
import com.client.woop.woop.fragments.streams.SearchStreamsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeFragment extends BaseFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListeFragment newInstance() {
        ListeFragment fragment = new ListeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ListeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liste, container, false);

        // create the TabHost that will contain the Tabs
        FragmentTabHost tabHost = (FragmentTabHost) v.findViewById(R.id.fragment_liste_tabhost);

        tabHost.setup(getContext(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.liste_tabs_mymusic)).setIndicator(getString(R.string.liste_tabs_mymusic), null),
                MyMusicFragment.class, null);

        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.liste_tabs_local)).setIndicator(getString(R.string.liste_tabs_local), null),
                LocalMusicFragment.class, null);

        return v;
    }

}
