package com.client.woop.woop.web;

import android.os.AsyncTask;

import com.client.woop.woop.ILogger;
import com.client.woop.woop.Logger;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by nico on 10/1/2015.
 */
public class NetworkFinder extends AsyncTask<Void, Void, String> {

    public interface NetworkFinderFoundService{
        public void serviceFound(String host);
    }

    private static String TAG = NetworkFinder.class.getSimpleName();
    private NetworkFinderFoundService _listener;
    private String _subnet;
    private int _steps;
    private int _step;
    private int _timeout = 1000;
    private ILogger _logger;

    public NetworkFinder(String subnet, int step, int steps, NetworkFinderFoundService listener){
        _logger = new Logger();
        _subnet = subnet;
        _steps = steps;
        _step = step;
        _listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {

        String host = "http://192.168.1.84";



        try {
            if (InetAddress.getByName(host).isReachable(_timeout)){
                System.out.println(host + " is reachable");
                _logger.info(TAG, String.format("Found Service on %s", host));
                return host;
            }
            else{
                _logger.debug(TAG, String.format("Host %s is not reachable.", host));
            }
        } catch (Exception e) {
            String error = e.toString();
        }



        /*int previousStep = (_step - 1 > 0 ) ? _step - 1 : 1;
        int from = (_step > 2) ? 1 :  _step - 1 * _steps;
        int to = _step * _steps;

        for(int i = from; i < to; i++){
            String host= _subnet + "." + i * _steps;
            _logger.debug(TAG, String.format("Searching for Host %s", host));

            try {
                if (InetAddress.getByName(host).isReachable(_timeout)){
                    System.out.println(host + " is reachable");
                    _logger.info(TAG, String.format("Found Service on %s", host));
                    return host;
                }
            } catch (IOException e) {
            }
        }*/
        return null;
    }

    protected void onPostExecute(String result){
        _listener.serviceFound(result);
    }
}
