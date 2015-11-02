package com.client.woop.woop.fragments.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.client.woop.woop.R;
import com.client.woop.woop.fragments.interfaces.ISettingsView;
import com.client.woop.woop.controller.SettingsController;
import com.client.woop.woop.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends BaseFragment implements ISettingsView{

    SettingsController _controller;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _controller = new SettingsController(this, woopServer(), navigation());

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn = (Button) v.findViewById(R.id.fragment_settings_search_woop_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.searchServer();
            }
        });

        btn = (Button) v.findViewById(R.id.fragment_settings_reset_server_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.resetServer();
            }
        });

        return v;
    }


    @Override
    public void showProgressBar(String title, String message) {
        super.showProgressbar(title, message);
    }

    @Override
    public void hideProgressBar() {
        super.hideProgressbar();
    }
}
