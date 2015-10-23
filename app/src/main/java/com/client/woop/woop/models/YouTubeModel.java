package com.client.woop.woop.models;


public class YouTubeModel {

    private String _id;
    private String _title;
    private String _description;
    private String _thumbnail;
    private boolean _showPlayNext;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_thumbnail() {
        return _thumbnail;
    }

    public void set_thumbnail(String _thumbnail) {
        this._thumbnail = _thumbnail;
    }

    public boolean is_showPlayNext() {
        return _showPlayNext;
    }

    public void set_showPlayNext(boolean _showPlayNext) {
        this._showPlayNext = _showPlayNext;
    }
}
