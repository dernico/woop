package com.client.woop.woop.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nico on 10/21/2015.
 */
public class PlayingInfo {
    public String Title;
    public String Cover;
    public String Webpath;
    public String Type;
    public double Volume;
    public boolean IsRandom;
    public boolean IsPlaying;

    public static PlayingInfo createFromJson(JSONObject json) {
        PlayingInfo info = new PlayingInfo();
        info.Title = null;
        info.Cover = null;
        info.Webpath = null;
        info.IsPlaying = false;
        info.IsRandom = false;

        if (json.has("title")){
            try {
                info.Title = json.getString("title");
            } catch (JSONException e) {
                info.Title = "no Title";
            }
        }

        if(json.has("cover")){
            try {
                info.Cover = json.getString("cover");
            } catch (JSONException e) {
                info.Cover = "";
            }
        }

        if(json.has("IsRandom")){
            try {
                info.IsRandom = json.getBoolean("IsRandom");
            } catch (JSONException e) {
                info.IsRandom = false;
            }
        }

        if(json.has("Volume")){
            try {
                info.Volume = json.getDouble("Volume");
            } catch (JSONException e) {
                info.Volume = -1;
            }
        }

        if(json.has("webpath")){
            try {
                info.Webpath = json.getString("webpath");
            } catch (JSONException e) {
                info.Webpath = "";
            }
        }

        if(json.has("IsPlaying")){
            try {
                info.IsPlaying = json.getBoolean("IsPlaying");
            } catch (JSONException e) {
                info.IsPlaying = false;
            }
        }

        if(json.has("type")){
            try {
                info.Type = json.getString("type");
            } catch (JSONException e) {
                info.Type = "";
            }
        }

        return info;
    }
}
