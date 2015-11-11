package com.client.woop.woop.data;

import android.os.AsyncTask;



public class DBThread extends AsyncTask<Void, Void, Void> {

    public interface IDoInBackground{
        void doInBackground();
    }

    private IDoInBackground _callback;

    public DBThread(IDoInBackground callback){
        _callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        _callback.doInBackground();
        return null;
    }
}
