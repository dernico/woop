package com.client.woop.woop.fragments.interfaces;


import com.client.woop.woop.extensions.EightTracksList;

public interface IEightTracksView {

    void setSerachResults(EightTracksList result);

    void showLoading();
    void hideLoading();
}
