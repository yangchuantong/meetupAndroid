//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.util.ela;

import org.elastos.meetuplib.storage.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpKit extends HttpUtils {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static String CHARSET = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(HttpKit.class);

    private HttpKit() {
    }


}
