package com.client.woop.woop.models;

/**
 * Created by nico on 9/30/2015.
 */
public class Person {

    private String Name;
    private String Surname;
    private String DisplayName;
    private String ImageUrl;
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
}
