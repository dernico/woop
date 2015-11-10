package com.client.woop.woop.data;

import android.os.AsyncTask;

public class DBThread extends AsyncTask<Void, Void, Void> {

    public interface IDBThreadCallback{
        void doInBackground();
    }

    private IDBThreadCallback _callback;

    public DBThread(IDBThreadCallback callback){
        _callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        _callback.doInBackground();
        return null;
    }

    protected void onPostExecute(Void result){
    }
}