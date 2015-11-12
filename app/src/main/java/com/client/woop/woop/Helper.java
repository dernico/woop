package com.client.woop.woop;


import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class Helper {

    private static ImageLoader _imageLoader;

    public static DisplayImageOptions imageLoaderOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.no_pic)
                .showImageOnFail(R.mipmap.no_pic)// resource or drawable
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(100))

                .build();

        return options;
    }


    public static ImageLoader initImageLoader(Context context){
        if(_imageLoader == null) {

            // Create global configuration and initialize ImageLoader with this config
            File cacheDir = StorageUtils.getCacheDirectory(context);
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
/*                    .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileCount(100)
                    .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())*/
                    .defaultDisplayImageOptions(imageLoaderOptions())
                    .writeDebugLogs()
                    .build();
            _imageLoader = ImageLoader.getInstance();
            _imageLoader.init(config);
        }
        return ImageLoader.getInstance();
    }

}
