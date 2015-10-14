package com.client.woop.woop.data;

import com.client.woop.woop.data.interfaces.IDeviceData;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


/**
 * Created by nico on 10/14/2015.
 */
public class DeviceData implements IDeviceData{
    /**
     * Get IP address from first non-localhost interface
     * @return  address or empty string
     */
    public String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String ipAdress = addr.getHostAddress().toUpperCase();

                        if(addr.isSiteLocalAddress()){
                            return ipAdress;
                        }

                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        /*if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }*/
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
