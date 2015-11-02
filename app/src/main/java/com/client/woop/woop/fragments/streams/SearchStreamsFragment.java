package com.client.woop.woop.fragments.streams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.client.woop.woop.R;
import com.client.woop.woop.adapter.TuneInAdapter;
import com.client.woop.woop.controller.SearchStreamsController;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.ISearchStreamsView;
import com.client.woop.woop.models.TuneInModel;
import com.client.woop.woop.models.YouTubeModel;

import java.util.List;

public class SearchStreamsFragment extends BaseFragment implements ISearchStreamsView {

    private SearchStreamsController _controller;
    private ListView _listview;
    private EditText _text;


    public static SearchStreamsFragment newInstance() {
        SearchStreamsFragment fragment = new SearchStreamsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_streams_search, container, false);


        _listview = (ListView) v.findViewById(R.id.fragment_streams_search_results);
        _text = (EditText) v.findViewById(R.id.fragment_streams_search_query);

        Button searchButton = (Button) v.findViewById(R.id.fragment_streams_search_button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.search();
            }
        });

        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _controller.play(position);
            }
        });

        _controller = new SearchStreamsController(this, woopServer());

        return v;
    }

    @Override
    public String getSearchString() {
        return _text.getText().toString();
    }

    @Override
    public void setSearchResult(List<TuneInModel> result) {
        _listview.setAdapter(new TuneInAdapter(getActivity(), result));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showProgress(String title, String message) {
        super.showProgressbar(title, message);
    }

    @Override
    public void hideProgress() {
        super.hideProgressbar();
    }
}
