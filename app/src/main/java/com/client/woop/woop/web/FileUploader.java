package com.client.woop.woop.web;

import android.os.AsyncTask;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class FileUploader extends AsyncTask<Void, Void, String> {

    public interface FileUploadListener{
        void fileUploaded(String result);
        void uploadFailed(Exception ex);
    }

    private final String _boundary;
    private static final String _LINE_FEED = "\r\n";
    private static String charset = "UTF-8";
    private HttpURLConnection _httpConn;
    private OutputStream _outputStream;
    private PrintWriter _writer;
    private String _requestURL;
    private String _fieldName;
    private File _uploadFile;
    private FileUploadListener _callback;
    private Exception _failure;

    /**
     * This constructor initializes a new HTTP POST request with content type
     * is set to multipart/form-data
     * @param requestURL
     */
    public FileUploader(String requestURL, String fieldName, File uploadFile, FileUploadListener listener) {
        _requestURL = requestURL;
        _fieldName = fieldName;
        _uploadFile = uploadFile;
        // creates a unique boundary based on time stamp
        _boundary = "===" + System.currentTimeMillis() + "===";
        _callback = listener;
    }


    @Override
    protected String doInBackground(Void... params) {

        try {
            URL url = null;
            url = new URL(_requestURL);
            _httpConn = (HttpURLConnection) url.openConnection();
            _httpConn.setUseCaches(false);
            _httpConn.setDoOutput(true); // indicates POST method
            _httpConn.setDoInput(true);
            _httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + _boundary);
            _httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
            _httpConn.setRequestProperty("Test", "Bonjour");
            _outputStream = _httpConn.getOutputStream();
            _writer = new PrintWriter(new OutputStreamWriter(_outputStream, charset),
                    true);


            String fileName = _uploadFile.getName();
            _writer.append("--" + _boundary).append(_LINE_FEED);
            _writer.append(
                    "Content-Disposition: form-data; name=\"" + _fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(_LINE_FEED);
            _writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(_LINE_FEED);
            _writer.append("Content-Transfer-Encoding: binary").append(_LINE_FEED);
            _writer.append(_LINE_FEED);
            _writer.flush();

            FileInputStream inputStream = new FileInputStream(_uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                _outputStream.write(buffer, 0, bytesRead);
            }
            _outputStream.flush();
            inputStream.close();

            _writer.append(_LINE_FEED);
            _writer.flush();


            String response = "";

            _writer.append(_LINE_FEED).flush();
            _writer.append("--" + _boundary + "--").append(_LINE_FEED);
            _writer.close();

            // checks server's status code first
            int status = 0;
            status = _httpConn.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        _httpConn.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();
                _httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }

            return response;
        } catch (IOException e) {
            _failure = e;
        }

        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(_failure == null) {
            _callback.fileUploaded(result);
        }
        else{
            _callback.uploadFailed(_failure);
        }
    }
}