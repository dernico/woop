package com.client.woop.woop.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.woop.woop.R;
import com.client.woop.woop.activitys.interfaces.ISavedStreamsView;
import com.client.woop.woop.adapter.FavoriteStreamAdapter;
import com.client.woop.woop.controller.SavedStreamsController;
import com.client.woop.woop.models.StreamModel;

import java.util.List;

public class SavedStreamsActivity extends BaseActivity implements ISavedStreamsView {

    private SavedStreamsController _controller;
    private ListView _listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_streams);

        _controller = new SavedStreamsController(this,woopServer());

        _listView = (ListView) findViewById(R.id.activity_saved_streams_listview);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _controller.playStream(position);
            }
        });
    }



    @Override
    public void setStreams(List<StreamModel> streams) {
        _listView.setAdapter(new FavoriteStreamAdapter(this, streams));
    }
}
