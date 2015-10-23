package com.client.woop.woop.controller;

import android.widget.Toast;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.ISearchStreamsView;
import com.client.woop.woop.models.TuneInModel;

import java.util.List;

/**
 * Created by nico on 10/23/2015.
 */
public class SearchStreamsController {

    ISearchStreamsView _view;
    IWoopServer _woop;

    public SearchStreamsController(ISearchStreamsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }


    public void search(){
        String query = _view.getSearchString();
        _woop.searchStream(query, new WoopServer.WoopDataReceived<List<TuneInModel>>() {
            @Override
            public void dataReceived(List<TuneInModel> result) {
                _view.setSearchResult(result);
            }

            @Override
            public void errorReceived(Exception ex) {
                _view.showToast(ex.toString());
            }
        });
    }

}
