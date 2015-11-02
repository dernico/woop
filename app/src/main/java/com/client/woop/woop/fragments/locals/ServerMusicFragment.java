package com.client.woop.woop.fragments.locals;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.woop.woop.R;
import com.client.woop.woop.adapter.MyMusicAdapter;
import com.client.woop.woop.controller.ServerMusicController;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.fragments.interfaces.IServerMusicView;
import com.client.woop.woop.models.ServerMusicModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServerMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServerMusicFragment extends BaseFragment implements IServerMusicView {

    private ServerMusicController _controller;
    private ListView _listview;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ServerMusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServerMusicFragment newInstance(String param1, String param2) {
        ServerMusicFragment fragment = new ServerMusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ServerMusicFragment() {
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
        View v = inflater.inflate(R.layout.fragment_my_music, container, false);

        _controller = new ServerMusicController(this, woopServer());
        _listview = (ListView) v.findViewById(R.id.fragment_mymusic_liste);
        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _controller.play(position);
            }
        });

        return v;
    }


    @Override
    public void setListData(List<ServerMusicModel> liste) {
        _listview.setAdapter(new MyMusicAdapter(getActivity(), liste));
    }
}
