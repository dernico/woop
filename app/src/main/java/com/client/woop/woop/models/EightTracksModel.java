package com.client.woop.woop.models;


import com.client.woop.woop.contracts.EightTracksContract;

import org.json.JSONException;
import org.json.JSONObject;

public class EightTracksModel {

    private String id;
    private String description;
    private String tags;
    private String cover;
    private String likes;
    private String name;

    public static final EightTracksModel createFromJSON(JSONObject json) throws JSONException {
        EightTracksModel model = new EightTracksModel();

        model.id = json.getString(EightTracksContract.id);
        model.description = json.getString(EightTracksContract.description);
        model.tags = json.getString(EightTracksContract.tags);
        model.cover = json.getString(EightTracksContract.cover);
        model.likes = json.getString(EightTracksContract.likes);
        model.name = json.getString(EightTracksContract.name);

        return model;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(EightTracksContract.id, id);
        json.put(EightTracksContract.description, description);
        json.put(EightTracksContract.tags, tags);
        json.put(EightTracksContract.cover, cover);
        json.put(EightTracksContract.likes, likes);
        json.put(EightTracksContract.name, name);

        return json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
