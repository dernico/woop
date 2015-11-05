package com.client.woop.woop.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.PlayControlsController;
import com.client.woop.woop.fragments.interfaces.IPlayControlsView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayControlsFragment extends BaseFragment implements IPlayControlsView{

    private PlayControlsController _controller;
    private ImageButton _playPauseButton;
    private ImageButton _prevButton;
    private ImageButton _nextButton;
    private ImageButton _shuffleButton;
    private ImageButton _volUpButton;
    private ImageButton _volDownButton;
    private TextView _volumeText;

    public PlayControlsFragment() {
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

        _playPauseButton = (ImageButton) v.findViewById(R.id.fragment_play_controls_play_pause);
        _prevButton = (ImageButton) v.findViewById(R.id.fragment_play_controls_prev);
        _nextButton = (ImageButton) v.findViewById(R.id.fragment_play_controls_next);
        _shuffleButton = (ImageButton) v.findViewById(R.id.fragment_play_controls_play_shuffle);
        _volumeText = (TextView) v.findViewById(R.id.fragment_play_controls_volume);
        _volUpButton = (ImageButton) v.findViewById(R.id.fragment_play_controls_play_volUp);
        _volDownButton = (ImageButton) v.findViewById(R.id.fragment_play_controls_play_volDown);
        
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

        _shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.shuffle();
            }
        });

        _volUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.volUp();
            }
        });

        _volDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.volDown();
            }
        });


        _controller = new PlayControlsController(this, woopServer());

        return v;
    }


    @Override
    public void setVolume(String volume) {
        if(_volumeText == null) return;

        _volumeText.setText(getString(R.string.fragment_play_controls_volume) + volume);
    }

    @Override
    public void setPlaying() {
        if(_playPauseButton == null) return;

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        _playPauseButton.setImageBitmap(image);
    }

    @Override
    public void setPause() {
        if(_playPauseButton == null) return;

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.play);
        _playPauseButton.setImageBitmap(image);
    }

    @Override
    public void setShuffleOn() {
        if(_shuffleButton == null) return;

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.shuffle);
        _shuffleButton.setImageBitmap(image);
    }

    @Override
    public void setShuffleOff() {
        if(_shuffleButton == null) return;

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.shuffle_off);
        _shuffleButton.setImageBitmap(image);
    }
}
