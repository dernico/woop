package com.client.woop.woop.models;

import com.client.woop.woop.contracts.StreamModelContract;

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
        model.id = json.getInt(StreamModelContract.id);
        model.description = json.getString(StreamModelContract.description);
        model.stream = json.getString(StreamModelContract.stream);
        model.image = json.getString(StreamModelContract.image);
        model.name = json.getString(StreamModelContract.name);

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
