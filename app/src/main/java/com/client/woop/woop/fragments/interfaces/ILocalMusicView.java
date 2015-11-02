package com.client.woop.woop.fragments.interfaces;

import com.client.woop.woop.models.LocalMusicModel;

import java.util.List;

public interface ILocalMusicView {

    void setLocalMusic(List<LocalMusicModel> localMusic);
    void showProgress(String title, String message);
    void hideProgress();

}
