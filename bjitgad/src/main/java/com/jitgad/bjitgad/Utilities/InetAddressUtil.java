
package com.jitgad.bjitgad.Utilities;


import com.jitgad.bjitgad.DataStaticBD.Configuration;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author jorge
 */
public class InetAddressUtil {
    
    /*
    Obtener la dirección del host
    */
    public String getHostIp(){

        String realIp = null;

        try {
            InetAddress address = InetAddress.getLocalHost();

            // Si es la dirección de la tarjeta de red de bucle invertido, obtenga la dirección ipv4
            if (address.isLoopbackAddress()) {
                address = getInet4Address();
            }

            realIp = address.getHostAddress();

            return address.getHostAddress();
        } catch (Exception e) {
            if (Configuration.DEBUG) {
                System.err.println(e.getMessage());
            }
        }

        return realIp;
    }
    
    /*
    Obtener la configuración de red IPV4 *
    */
    private static InetAddress getInet4Address() throws SocketException {
        // Obtén toda la información de la tarjeta de red
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip instanceof Inet4Address) {
                    return ip;
                }
            }
        }
        return null;
    }
    
    
}
