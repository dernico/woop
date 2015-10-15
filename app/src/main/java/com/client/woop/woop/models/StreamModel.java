package com.client.woop.woop.models;

import org.json.JSONException;
import org.json.JSONObject;

public class StreamModel {

    private int id;
    private String description;
    private String stream;
    private String image;
    private String name;

    public static StreamModel createFromJson(JSONObject json) throws JSONException {
        StreamModel model = new StreamModel();
        model.id = json.getInt("id");
        model.description = json.getString("description");
        model.stream = json.getString("stream");
        model.image = json.getString("image");
        model.name = json.getString("name");

        return model;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStream() {
        return stream;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
