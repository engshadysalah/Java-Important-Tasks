/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.services;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

/**
 *
 * @author Mohamed Shehata
 */

public class SSLClientBuilder {

    public static Client newClient(Configuration config) {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            try {
                ctx.init(null, certs, new SecureRandom());
            } catch (KeyManagementException ex) {
                Logger.getLogger(SSLClientBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return ClientBuilder.newBuilder()
                    .withConfig(config)
                    .hostnameVerifier(new TrustAllHostNameVerifier())
                    .sslContext(ctx)
                    .build();
        } catch (NoSuchAlgorithmException  ex) {
            Logger.getLogger(SSLClientBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static TrustManager[] certs = new TrustManager[]{
        new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

        }
    };

    static Client newClient() {
        ClientConfig clientConfig = new ClientConfig();
    // values are in milliseconds
    clientConfig.property(ClientProperties.READ_TIMEOUT, 10000);
    clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        return newClient( clientConfig);
       
    }

    public static class TrustAllHostNameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    }

}
