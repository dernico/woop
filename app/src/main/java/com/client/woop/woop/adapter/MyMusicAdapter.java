package com.client.woop.woop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.woop.woop.Helper;
import com.client.woop.woop.R;
import com.client.woop.woop.models.ServerMusicModel;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class MyMusicAdapter extends ArrayAdapter<ServerMusicModel> {

    private List<ServerMusicModel> _mymusic;
    private Context _context;

    public MyMusicAdapter(Context ctx, List<ServerMusicModel> mymusic){
        super(ctx, R.layout.adapter_mymusic_item, mymusic);

        _context = ctx;
        _mymusic = mymusic;

    }

    @Override
    public int getCount() {
        return _mymusic.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater layout = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layout.inflate(R.layout.adapter_mymusic_item ,null);
        }

        ServerMusicModel stream = _mymusic.get(position);

        TextView tv = (TextView) v.findViewById(R.id.adapter_mymusic_item_title);
        tv.setText(stream.get_title());

        tv = (TextView) v.findViewById(R.id.adapter_mymusic_item_artist);
        tv.setText(stream.get_artist());

        tv = (TextView) v.findViewById(R.id.adapter_mymusic_item_album);
        tv.setText(stream.get_album());

        ImageView image = (ImageView) v.findViewById(R.id.adapter_mymusic_item_image);
        ImageLoader.getInstance().displayImage(stream.get_cover(), image);

        return v;
    }
}