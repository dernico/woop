package com.client.woop.woop.controller;

import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.fragments.interfaces.ISearchStreamsView;
import com.client.woop.woop.models.PlayingInfoModel;
import com.client.woop.woop.models.TuneInModel;

import java.util.List;


public class SearchStreamsController {

    ISearchStreamsView _view;
    IWoopServer _woop;
    List<TuneInModel> _currentResult;

    public SearchStreamsController(ISearchStreamsView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }


    public void search(){
        _view.showProgress("Find Stream", "Searching for your input ...");
        String query = _view.getSearchString();
        _woop.searchTuneInStream(query, new WoopServer.WoopDataReceived<List<TuneInModel>>() {
            @Override
            public void dataReceived(List<TuneInModel> result) {
                _currentResult = result;
                _view.setSearchResult(_currentResult);
                _view.hideProgress();
            }

            @Override
            public void errorReceived(Exception ex) {
                _view.hideProgress();
                _view.showToast(ex.toString());
            }
        });
    }

    public void play(int position){
        TuneInModel model = _currentResult.get(position);
        _woop.playTuneInStream(model, new WoopServer.WoopDataReceived<PlayingInfoModel>() {
            @Override
            public void dataReceived(PlayingInfoModel result) {
                //Hmm don't need to do anything here ?!
            }

            @Override
            public void errorReceived(Exception ex) {
                //Todo: handle error properly
            }
        });
    }

}
