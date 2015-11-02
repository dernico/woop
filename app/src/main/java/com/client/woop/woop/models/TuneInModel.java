package com.client.woop.woop.models;


import org.json.JSONException;
import org.json.JSONObject;

public class TuneInModel {
    private String _id;
    private String _stream;
    private String _image;
    private String _name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_stream() {
        return _stream;
    }

    public void set_stream(String _stream) {
        this._stream = _stream;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public static TuneInModel create(JSONObject json) throws JSONException {
        TuneInModel model = new TuneInModel();
        model._id = json.getString("id");
        model._image = json.getString("image");
        model._name = json.getString("name");
        model._stream = json.getString("stream");
        return model;
    }

    public String toJSONString() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", _id);
        json.put("image", _image);
        json.put("name", _name);
        json.put("stream", _stream);
        return json.toString();
    }
}
