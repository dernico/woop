package com.client.woop.woop.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.PlayControlsController;
import com.client.woop.woop.fragments.interfaces.IPlayControlsView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayControls extends BaseFragment implements IPlayControlsView{

    private PlayControlsController _controller;
    private Button _playPauseButton;
    private Button _prevButton;
    private Button _nextButton;
    private Button _shuffleButton;
    private TextView _volumeText;

    public PlayControls() {
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
        View v = inflater.inflate(R.layout.fragment_play_controls, container, false);

        _controller = new PlayControlsController(this, woopServer());
        _playPauseButton = (Button) v.findViewById(R.id.fragment_play_controls_play_pause);
        _prevButton = (Button) v.findViewById(R.id.fragment_play_controls_prev);
        _nextButton = (Button) v.findViewById(R.id.fragment_play_controls_next);
        _shuffleButton = (Button) v.findViewById(R.id.fragment_play_controls_play_shuffle);
        _volumeText = (TextView) v.findViewById(R.id.fragment_play_controls_volume);
        
        _playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.play_pause();
            }
        });
        
        _prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.prev();
            }
        });

        _nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.next();
            }
        });

        return v;
    }


    @Override
    public void setVolume(String volume) {
        _volumeText.setText(volume);
    }

    @Override
    public void setPlaying() {
        _playPauseButton.setText(getString(R.string.fragment_play_controls_pauseText));
    }

    @Override
    public void setPause() {
        _playPauseButton.setText(getString(R.string.fragment_play_controls_playText));
    }
}
