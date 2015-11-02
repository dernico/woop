package com.client.woop.woop.web;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HttpRequest extends AsyncTask<Void, Void, String> {

    public interface DownloadCompleteListener {
        void completionCallBack(HttpOptions options, String result);
        void errorCallBack(HttpOptions options);
    }


    private DownloadCompleteListener _listener;
    private HttpOptions _options;

    public HttpRequest(HttpOptions options, DownloadCompleteListener aListener) {
        _listener = aListener;
        _options = options;
    }

    @Override
    protected String doInBackground(Void... params) {
        InputStream is = null;

        try {
            URL url = new URL(_options.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(_options.timeout /* milliseconds */);
            conn.setConnectTimeout(_options.timeout /* milliseconds */);
            conn.setRequestMethod(_options.type);
            conn.setDoInput(true);

            if(_options.type == HttpRequestType.POST.toString()) {
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write(getQuery(_options.postData));
                writer.flush();
                writer.close();
                os.close();
            }

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("HttpRequest", "The response code is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            _options.setError(e);
        } catch (ProtocolException e) {
            _options.setError(e);
        } catch (UnsupportedEncodingException e) {
            _options.setError(e);
        } catch (IOException e) {
            _options.setError(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    _options.setError(e);
                }
            }
        }
        return null;
    }

    private String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String,String> pairs = iterator.next();
            String value =  pairs.getValue();
            String key = pairs.getKey();

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));
        }

        return result.toString();
    }

    private String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String str;
        String result = "";

        while( (str = reader.readLine()) != null){
            result += str;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(_options.hasError){
            _listener.errorCallBack(_options);
        }
        else if (!isCancelled()) {
            _listener.completionCallBack(_options, result);
        }
    }
}