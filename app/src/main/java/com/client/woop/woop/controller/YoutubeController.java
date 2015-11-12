package com.client.woop.woop.controller;


import com.client.woop.woop.data.WoopServer;
import com.client.woop.woop.data.interfaces.IWoopServer;
import com.client.woop.woop.extensions.YoutubeList;
import com.client.woop.woop.fragments.interfaces.IYoutubeView;
import com.client.woop.woop.models.YouTubeModel;

public class YoutubeController {

    private IYoutubeView _view;
    private IWoopServer _woop;
    private YoutubeList _searchResults;

    public YoutubeController(IYoutubeView view, IWoopServer woop){
        _view = view;
        _woop = woop;
    }

    public void search(String searchtermn){
        _woop.youtubeSearch(searchtermn, new WoopServer.WoopDataReceived<YoutubeList>() {
            @Override
            public void dataReceived(YoutubeList result) {
                _searchResults = result;
                _view.setSearchResult(result);
            }

            @Override
            public void errorReceived(Exception ex) {
                //TODO: proper handling
            }
        });
    }

    public void play(int position){
        if(_searchResults != null && _searchResults.size() > 0) {
            YouTubeModel model = _searchResults.get(position);

        }
    }

}
