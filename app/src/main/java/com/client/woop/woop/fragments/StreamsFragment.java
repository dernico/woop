package com.client.woop.woop.fragments;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.client.woop.woop.R;
import com.client.woop.woop.activitys.SavedStreamsActivity;
import com.client.woop.woop.adapter.FavoriteStreamAdapter;
import com.client.woop.woop.controller.StreamsController;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.fragments.interfaces.IStreamsView;
import com.client.woop.woop.models.StreamModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StreamsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamsFragment extends BaseFragment implements IStreamsView{

    private StreamsController _controller;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StreamsFragment.
     */
    public static StreamsFragment newInstance() {
        StreamsFragment fragment = new StreamsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public StreamsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_streams, container, false);

        _controller = new StreamsController(this,_woop);


        // create the TabHost that will contain the Tabs
        FragmentTabHost tabHost = (FragmentTabHost) v.findViewById(R.id.fragment_streams_tabhost);

        tabHost.setup(getContext(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec("tab1").setIndicator(getString(R.string.streams_tabs_saved), null),
                YouTubeFragment.class, null);

        tabHost.addTab(
                tabHost.newTabSpec("tab2").setIndicator(getString(R.string.streams_tabs_search), null),
                ListeFragment.class, null);

        return v;
    }
}
