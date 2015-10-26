package com.client.woop.woop.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MyMusicModel {
    private int _id;
    private String _album;
    private boolean _isLocal;
    private String _artist;
    private String _title;
    private String _cover;
    private boolean _isnext;
    private String _webpath;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_album() {
        return _album;
    }

    public void set_album(String _album) {
        this._album = _album;
    }

    public boolean is_isLocal() {
        return _isLocal;
    }

    public void set_isLocal(boolean _isLocal) {
        this._isLocal = _isLocal;
    }

    public String get_artist() {
        return _artist;
    }

    public void set_artist(String _artist) {
        this._artist = _artist;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_cover() {
        return _cover;
    }

    public void set_cover(String _cover) {
        this._cover = _cover;
    }

    public boolean is_isnext() {
        return _isnext;
    }

    public void set_isnext(boolean _isnext) {
        this._isnext = _isnext;
    }

    public String get_webpath() {
        return _webpath;
    }

    public void set_webpath(String _webpath) {
        this._webpath = _webpath;
    }

    public static MyMusicModel create(JSONObject json) throws JSONException {
        MyMusicModel model = new MyMusicModel();

        model.set_id(json.getInt("id"));
        model.set_album(json.getString("album"));
        model.set_artist(json.getString("artist"));
        model.set_cover(json.getString("cover"));
        model.set_isLocal(json.getBoolean("islocal"));
        model.set_title(json.getString("title"));
        model.set_isnext(json.getBoolean("isnext"));
        model.set_webpath(json.getString("webpath"));

        return model;
    }
}
