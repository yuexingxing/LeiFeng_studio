package com.tajiang.leifeng.common.retrofit.ssl;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.utils.LogUtils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Admins on 2017/2/10.
 */

public class TJSSLUtil {

    private static final String CLIENT_TRUST_PASSWORD = "changeit";//信任证书密码，该证书默认密码是changeit
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509"; //SSL加密
    private static final String CLIENT_TRUST_KEYSTORE = "BKS"; //证书类型
    private static final int[] CERTIFICATES = {R.raw.ssl_support}; //certificates 是工程下证书资源ID, int[] certificates = {R.raw.ssl_support}

    /**
     *
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        CertificateFactory certificateFactory;
        SSLContext sslContext = null;

        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < CERTIFICATES.length; i++) {
                InputStream certificate = TJApp.getIns().getResources().openRawResource(CERTIFICATES[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));

                if (certificate != null) {
                    certificate.close();
                }
            }
            sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (Exception e) {
            LogUtils.e("SslContextFactory", e.getMessage());
        }
        return sslContext.getSocketFactory();
    }

}
