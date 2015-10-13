package com.client.woop.woop.web;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class StringDownloader extends AsyncTask<Void, Void, String> {

    public interface DownloadCompleteListener {
        void completionCallBack(String uri, String result);
    }

    private DownloadCompleteListener listener;
    private String _link;
    private int _timeout;

    public StringDownloader (String aLink, int timeout, DownloadCompleteListener aListener) {
        listener = aListener;
        _link = aLink;
        _timeout = timeout;
    }

    @Override
    protected String doInBackground(Void... params) {
        InputStream is = null;

        try {
            URL url = new URL(_link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(_timeout /* milliseconds */);
            conn.setConnectTimeout(_timeout /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String str;
        String result = null;

        while( (str = reader.readLine()) != null){
            result += str;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (!isCancelled() && result != null) {
            listener.completionCallBack(_link, result);
        }
    }
}