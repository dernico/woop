package com.client.woop.woop.fragments.eighttracks;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.woop.woop.R;
import com.client.woop.woop.adapter.EightTracksAdapter;
import com.client.woop.woop.controller.EightTracksController;
import com.client.woop.woop.extensions.EightTracksList;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.IEightTracksView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EightTracksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EightTracksFragment extends BaseFragment implements IEightTracksView {

    private EightTracksController _contoller;
    private ListView _tracksResultsList;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EightTracksFragment.
     */
    public static EightTracksFragment newInstance() {
        EightTracksFragment fragment = new EightTracksFragment();
        return fragment;
    }

    public EightTracksFragment() {
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
        View v = inflater.inflate(R.layout.fragment_eight_tracks, container, false);

        _tracksResultsList = (ListView) v.findViewById(R.id.fragment_eight_tracks_search_results);
        _tracksResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _contoller.play(position);
            }
        });

        _contoller = new EightTracksController(this, woopServer());
        _contoller.searchPopular();
        return v;
    }

    @Override
    public void setSerachResults(EightTracksList result) {
        Activity activity = getActivity();
        if(activity == null) return;
        _tracksResultsList.setAdapter(new EightTracksAdapter(activity, result));
    }

    @Override
    public void showLoading() {
        super.showProgressbar(getString(R.string.Loading), "....");
    }

    @Override
    public void hideLoading() {
        super.hideProgressbar();
    }
}
