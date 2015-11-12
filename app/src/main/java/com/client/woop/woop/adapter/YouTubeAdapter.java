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
import com.client.woop.woop.models.StreamModel;
import com.client.woop.woop.models.YouTubeModel;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;


public class YouTubeAdapter extends ArrayAdapter<YouTubeModel> {

    private List<YouTubeModel> _streams;
    private Context _context;

    public YouTubeAdapter(Context ctx, List<YouTubeModel> streams){
        super(ctx, R.layout.adapter_youtube_item, streams);

        _context = ctx;
        _streams = streams;

    }

    @Override
    public int getCount() {
        return _streams.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater layout = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layout.inflate(R.layout.adapter_youtube_item ,null);
        }

        YouTubeModel stream = _streams.get(position);

        TextView tv = (TextView) v.findViewById(R.id.adapter_youtube_item_text);
        tv.setText(stream.get_title());

        ImageView image = (ImageView) v.findViewById(R.id.adapter_youtube_item_image);
        ImageLoader.getInstance().displayImage(stream.get_thumbnail(), image, Helper.imageLoaderOptions());

        return v;
    }
}
