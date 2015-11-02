package com.client.woop.woop.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.woop.woop.R;
import com.client.woop.woop.controller.PlayingInfoController;
import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.fragments.interfaces.IPlayingInfoView;
import com.client.woop.woop.models.PlayingInfoModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PlayingInfoFragment extends BaseFragment implements IPlayingInfoView {

    ImageLoader _imageLoader;
    ImageView _imageView;
    TextView _textView;
    PlayingInfoModel _currentInfo;

    public PlayingInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_playing_info, container, false);

        _imageView = (ImageView) v.findViewById(R.id.fragment_playing_info_cover);
        _textView = (TextView) v.findViewById(R.id.fragment_playing_info_text);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .build();
        _imageLoader = ImageLoader.getInstance();
        _imageLoader.init(config);

        new PlayingInfoController(this, woopServer());
        return v;
    }

    @Override
    public void setPlayingInfo(PlayingInfoModel info) {
        _textView.setText(info.Title);

        if(_currentInfo == null){
            _imageView.setImageBitmap(null);
            _imageLoader.displayImage(info.Cover, _imageView);
        }
        else if(info.Cover.compareTo(_currentInfo.Cover) != 0){
            _imageView.setImageBitmap(null);
            _imageLoader.displayImage(info.Cover, _imageView);
        }

        _currentInfo = info;
    }
}
