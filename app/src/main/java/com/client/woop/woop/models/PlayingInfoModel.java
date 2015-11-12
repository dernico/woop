package com.client.woop.woop.models;

import com.client.woop.woop.contracts.PlayingInfoContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nico on 10/21/2015.
 */
public class PlayingInfoModel {
    public String Title;
    public String Artist;
    public String Cover;
    public String Webpath;
    public String Type;
    public double Volume;
    public boolean IsRandom;
    public boolean IsPlaying;

    public static PlayingInfoModel createFromJson(JSONObject json) {
        PlayingInfoModel info = new PlayingInfoModel();
        info.Title = null;
        info.Artist = null;
        info.Cover = null;
        info.Webpath = null;
        info.IsPlaying = false;
        info.IsRandom = false;


        if (json.has(PlayingInfoContract.artist)){
            try {
                info.Artist = json.getString(PlayingInfoContract.artist);
            } catch (JSONException e) {
                info.Artist = "";
            }
        }

        if (json.has(PlayingInfoContract.title)){
            try {
                info.Title = json.getString(PlayingInfoContract.title);
            } catch (JSONException e) {
                info.Title = "no Title";
            }
        }

        if(json.has(PlayingInfoContract.cover)){
            try {
                info.Cover = json.getString(PlayingInfoContract.cover);
            } catch (JSONException e) {
                info.Cover = "";
            }
        }

        if(json.has(PlayingInfoContract.isRandom)){
            try {
                info.IsRandom = json.getBoolean(PlayingInfoContract.isRandom);
            } catch (JSONException e) {
                info.IsRandom = false;
            }
        }

        if(json.has(PlayingInfoContract.volume)){
            try {
                info.Volume = json.getDouble(PlayingInfoContract.volume);
            } catch (JSONException e) {
                info.Volume = -1;
            }
        }

        if(json.has(PlayingInfoContract.webpath)){
            try {
                info.Webpath = json.getString(PlayingInfoContract.webpath);
            } catch (JSONException e) {
                info.Webpath = "";
            }
        }

        if(json.has(PlayingInfoContract.isPlaying)){
            try {
                info.IsPlaying = json.getBoolean(PlayingInfoContract.isPlaying);
            } catch (JSONException e) {
                info.IsPlaying = false;
            }
        }

        if(json.has(PlayingInfoContract.type)){
            try {
                info.Type = json.getString(PlayingInfoContract.type);
            } catch (JSONException e) {
                info.Type = "";
            }
        }

        return info;
    }
}
