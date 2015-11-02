package com.client.woop.woop.fragments.streams;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.StreamsController;
import com.client.woop.woop.fragments.BaseFragment;
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


        // create the TabHost that will contain the Tabs
        FragmentTabHost tabHost = (FragmentTabHost) v.findViewById(R.id.fragment_streams_tabhost);

        tabHost.setup(getContext(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.streams_tabs_saved)).setIndicator(getString(R.string.streams_tabs_saved), null),
                SavedStreamsFragment.class, null);

        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.streams_tabs_search)).setIndicator(getString(R.string.streams_tabs_search), null),
                SearchStreamsFragment.class, null);

        _controller = new StreamsController(this,woopServer());
        return v;
    }
}
