package com.client.woop.woop.models;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nico on 9/30/2015.
 */
public class PersonModel {

    private String Name;
    private String Surname;
    private String DisplayName;
    private String ImageUrl;
    private byte[] Image;
    private String Id;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getId() {
        return Id;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("name", Name);
        json.put("surname", Surname);
        json.put("displayName", DisplayName);
        json.put("imageUrl", ImageUrl);
        json.put("id", Id);
        json.put("image", Base64.encodeToString(Image, Base64.DEFAULT));

        return json;
    }

    public static PersonModel createFromJSON(String json) throws JSONException {
        return createFromJSON(new JSONObject(json));
    }
    public static PersonModel createFromJSON(JSONObject json) throws JSONException {
        PersonModel person = new PersonModel();

        person.Id = json.getString("id");
        person.Name = json.getString("name");
        person.Surname = json.getString("surname");
        person.DisplayName = json.getString("displayName");
        person.ImageUrl = json.getString("imageUrl");

        String encodedString = json.getString("image");
        person.Image = Base64.decode(encodedString,Base64.DEFAULT);


        return person;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}
