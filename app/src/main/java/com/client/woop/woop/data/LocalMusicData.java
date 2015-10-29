package com.client.woop.woop.data;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.client.woop.woop.data.interfaces.ILocalMusicData;
import com.client.woop.woop.models.LocalMusicModel;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicData implements ILocalMusicData{

    private Activity _activity;
    private List<LocalMusicModel> _localMusic;

    public LocalMusicData(Activity activity){
        _activity = activity;
    }

    public void loadMusic(){

        ContentResolver musicResolver = _activity.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);

            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);

            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);

            int dataColum = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            _localMusic = new ArrayList<>();
            //add songs to list
            do {
                long id = musicCursor.getLong(idColumn);
                String title = musicCursor.getString(titleColumn);
                String artist = musicCursor.getString(artistColumn);
                String album = musicCursor.getString(albumColumn);
                String uri = musicCursor.getString(dataColum);
                _localMusic.add(new LocalMusicModel(id, title,album,artist, uri));
            }
            while (musicCursor.moveToNext());
        }
    }

    @Override
    public List<LocalMusicModel> getLocalMusic() {
        if(_localMusic == null){
            loadMusic();
        }
        return _localMusic;
    }
}
