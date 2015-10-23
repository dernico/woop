package com.client.woop.woop.fragments.interfaces;

import com.client.woop.woop.models.TuneInModel;

import java.util.List;

public interface ISearchStreamsView {

    String getSearchString();
    void setSearchResult(List<TuneInModel> result);
    void showToast(String msg);
}
