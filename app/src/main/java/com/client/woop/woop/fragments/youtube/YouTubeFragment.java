package com.client.woop.woop.fragments.youtube;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.client.woop.woop.R;
import com.client.woop.woop.adapter.YouTubeAdapter;
import com.client.woop.woop.controller.YoutubeController;
import com.client.woop.woop.extensions.YoutubeList;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.IYoutubeView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YouTubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YouTubeFragment extends BaseFragment implements IYoutubeView {

    private Button _searchButton;
    private TextView _searchText;
    private ListView _searchResultList;

    private YoutubeController _controller;


    public static YouTubeFragment newInstance() {
        YouTubeFragment fragment = new YouTubeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public YouTubeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_you_tube, container, false);

        _searchText = (TextView) v.findViewById(R.id.fragment_youtube_search_query);
        _searchButton = (Button) v.findViewById(R.id.fragment_youtube_search_button_search);
        _searchResultList = (ListView) v.findViewById(R.id.fragment_youtube_search_results);

        _searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.search(_searchText.getText().toString());
            }
        });

        _searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        _controller = new YoutubeController(this, woopServer());
        return v;
    }

    @Override
    public void setSearchResult(YoutubeList result) {

        Activity activity = getActivity();
        if(activity == null) return;
        _searchResultList.setAdapter(new YouTubeAdapter(activity, result));
    }
}
