package com.client.woop.woop.models;

import android.app.LocalActivityManager;

public class LocalMusicModel {

    private long _id;
    private String _title;
    private String _album;
    private String _artist;
    private String _uri;

    public LocalMusicModel(long id, String title, String album, String artist, String uri){
        _id = id;
        _title = title;
        _album = album;
        _artist = artist;
        _uri = uri;
    }

    public long get_id() {
        return _id;
    }

    public String get_title() {
        return _title;
    }

    public String get_album() {
        return _album;
    }

    public String get_artist() {
        return _artist;
    }

    public String get_uri() {
        return _uri;
    }
}
