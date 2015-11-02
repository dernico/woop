package com.client.woop.woop.fragments.locals;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.woop.woop.R;
import com.client.woop.woop.adapter.LocalMusicAdapter;
import com.client.woop.woop.controller.LocalMusicController;
import com.client.woop.woop.data.LocalMusicData;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.ILocalMusicView;
import com.client.woop.woop.models.LocalMusicModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalMusicFragment extends BaseFragment implements ILocalMusicView {

    private LocalMusicController _controller;
    private ListView _listview;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocalMusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalMusicFragment newInstance() {
        LocalMusicFragment fragment = new LocalMusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LocalMusicFragment() {
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
        View v = inflater.inflate(R.layout.fragment_local_music, container, false);

        _listview = (ListView) v.findViewById(R.id.fragment_local_music_listview);
        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _controller.playTrackOnServer(position);
            }
        });


        _controller = new LocalMusicController(this, new LocalMusicData(getActivity()), woopServer());
        _controller.loadLocalMusic();

        return v;
    }

    @Override
    public void setLocalMusic(List<LocalMusicModel> localMusic) {
        _listview.setAdapter(new LocalMusicAdapter(getActivity(), localMusic));
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
