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
import com.client.woop.woop.extensions.EightTracksList;
import com.client.woop.woop.models.EightTracksModel;
import com.client.woop.woop.models.YouTubeModel;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class EightTracksAdapter extends ArrayAdapter<EightTracksModel> {

    private EightTracksList _mixes;
    private Context _context;
    private ImageLoader _imageLoader;

    public EightTracksAdapter(Context ctx, EightTracksList mixes){
        super(ctx, R.layout.adapter_eighttracks_item, mixes);

        _context = ctx;
        _mixes = mixes;
    }

    @Override
    public int getCount() {
        return _mixes.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater layout = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layout.inflate(R.layout.adapter_eighttracks_item ,null);
        }

        EightTracksModel mix = _mixes.get(position);

        TextView tv = (TextView) v.findViewById(R.id.adapter_eighttracks_item_text);
        tv.setText(mix.getName());

        ImageView image = (ImageView) v.findViewById(R.id.adapter_eighttracks_item_image);
        ImageLoader.getInstance().displayImage(mix.getCover(), image, Helper.imageLoaderOptions());

        return v;
    }
}