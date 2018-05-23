package com.macaitech.beanpay.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http连接池
 */
public class HttpPoolManage {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPoolManage.class);

    private static final int TIME_OUT = 5000;

    private static final int CONNECT_TIME_OUT = 3000;

    private static MultiThreadedHttpConnectionManager CONNECTIONMANAGER;

    private static HttpClientParams CLIENTPARAMS;

    private static final String CONTENTTYPE_JSON = "application/json";

    private static final String CHARSET_UTF_8 = "UTF-8";

    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";

    static {
        init();
    }

    private static void init() {
        try {
            CONNECTIONMANAGER = new MultiThreadedHttpConnectionManager();
            HttpConnectionManagerParams params = CONNECTIONMANAGER.getParams();
            params.setDefaultMaxConnectionsPerHost(8000);
            params.setConnectionTimeout(CONNECT_TIME_OUT);
            params.setSoTimeout(TIME_OUT);
            params.setMaxTotalConnections(8000);
            CLIENTPARAMS = new HttpClientParams();
            CLIENTPARAMS.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            CLIENTPARAMS.setSoTimeout(TIME_OUT);
            CLIENTPARAMS.setConnectionManagerTimeout(CONNECT_TIME_OUT);
            CLIENTPARAMS.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET_UTF_8);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 释放连接池中连接,其实释放不了
     */
    public static void dispose(HttpClient httpClient) {
        httpClient.getHttpConnectionManager().closeIdleConnections(0);
    }

    /**
     * 获取一条http连接
     */
    public static HttpClient getHttpClient() {
        HttpClient httpClient = new HttpClient(CLIENTPARAMS, CONNECTIONMANAGER);
        return httpClient;
    }

    /**
     * 释放当前连接到连接池中
     *
     * @param httpMethod 请求对象
     */
    public static void disposeHttpClient(HttpMethod httpMethod) {
        httpMethod.releaseConnection();
    }

    /**
     * 简单发送get请求
     */
    public static String sendGet(String url) {
        GetMethod method = null;
        String response = "";
        try {
            HttpClient httpClient = getHttpClient();
            method = new CustomGetMethod(url);
            method.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
            method.addRequestHeader("User-Agent", USERAGENT);
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }

    public static String tryMoreSendGet(String url) {
        String response = "";
        for (int i = 0; i < 3; i++) {
            response = sendGet(url);
            if (response.length() != 0) {
                return response;
            }
        }
        return response;
    }

    /**
     * 简单发送post请求
     */
    public static String sendPost(String url, String content) {
        PostMethod method = null;
        String response = "";
        try {
            HttpClient httpClient = getHttpClient();
            method = new PostMethod(url);
            method.addRequestHeader("Content-Type", CONTENTTYPE_JSON);
            method.setRequestBody(content);
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }

    public static String tryMoreSendPost(String url, String content) {
        String response = "";
        for (int i = 0; i < 3; i++) {
            response = sendPost(url, content);
            if (response.length() != 0) {
                return response;
            }
        }
        return response;
    }
}
