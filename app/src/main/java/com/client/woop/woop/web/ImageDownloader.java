package com.client.woop.woop.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    public interface ImageDownloadedListener{
        void downloaded(Bitmap bitmap);
    }

    ImageDownloadedListener _listener;

    public ImageDownloader(ImageDownloadedListener listener) {
        _listener = listener;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        _listener.downloaded(result);
    }
}