package com.client.woop.woop.models;

import com.client.woop.woop.contracts.ServerMusicContract;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerMusicModel {
    private int _id;
    private String _album;
    private boolean _isLocal;
    private String _artist;
    private String _title;
    private String _cover;
    private boolean _isnext;
    private String _webpath;


    public static ServerMusicModel create(JSONObject json) throws JSONException {
        ServerMusicModel model = new ServerMusicModel();

        model.set_id(json.getInt(ServerMusicContract.id ));
        model.set_album(json.getString(ServerMusicContract.album));
        model.set_artist(json.getString(ServerMusicContract.artist));
        model.set_cover(json.getString(ServerMusicContract.cover));
        model.set_isLocal(json.getBoolean(ServerMusicContract.islocal));
        model.set_title(json.getString(ServerMusicContract.title));
        model.set_isnext(json.getBoolean(ServerMusicContract.isnext));
        model.set_webpath(json.getString(ServerMusicContract.webpath));

        return model;
    }


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
}
