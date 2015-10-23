package com.client.woop.woop.fragments.streams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.woop.woop.R;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.ISavedStreamsView;
import com.client.woop.woop.adapter.FavoriteStreamAdapter;
import com.client.woop.woop.controller.SavedStreamsController;
import com.client.woop.woop.models.StreamModel;

import java.util.List;

public class SavedStreamsFragment extends BaseFragment implements ISavedStreamsView {

    private SavedStreamsController _controller;
    private ListView _listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_streams_saved, container, false);

        _controller = new SavedStreamsController(this, woopServer());

        _listView = (ListView) v.findViewById(R.id.activity_saved_streams_listview);
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
    public void setStreams(List<StreamModel> streams) {
        _listView.setAdapter(new FavoriteStreamAdapter(getActivity(), streams));
    }
}
