package com.client.woop.woop.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.woop.woop.R;
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

    private ListView _listView;

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
        _listView = (ListView) v.findViewById(R.id.fragment_streams_list);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _controller.playStream(position);
            }
        });

        _controller.loadStreams();
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void setStreams(List<StreamModel> streams) {
        _listView.setAdapter(new FavoriteStreamAdapter(getActivity(), streams));
    }
}
