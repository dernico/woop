package com.client.woop.woop.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.StreamsController;
import com.client.woop.woop.fragments.interfaces.IStreamsView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StreamsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamsFragment extends BaseFragment implements IStreamsView{

    private StreamsController _controller;

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

        _controller = new StreamsController(this,woopServer());

        // create the TabHost that will contain the Tabs
        FragmentTabHost tabHost = (FragmentTabHost) v.findViewById(R.id.fragment_streams_tabhost);

        tabHost.setup(getContext(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec("tab1").setIndicator(getString(R.string.streams_tabs_saved), null),
                SavedStreamsFragment.class, null);

        tabHost.addTab(
                tabHost.newTabSpec("tab2").setIndicator(getString(R.string.streams_tabs_search), null),
                ListeFragment.class, null);

        return v;
    }
}
