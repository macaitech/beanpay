package com.macaitech.beanpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Http访问工具类
 *
 */
public class HttpUtil {
	
	private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String METHOD_POST = "POST";

    private static final String METHOD_GET = "GET";

    private static final int CONNECTTIMEOUT = 25000;

    private static final int READTIMEOUT = 25000;
    
    // 证书管理
    private static class DefaultTrustManager implements X509TrustManager {

    	@Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] cert, String oauthType) throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] cert, String oauthType) throws java.security.cert.CertificateException {
        }
    }
    
    /**
     * 创建连接
     * @param url
     * @param method
     * @param contentType
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(URL url, String method, String contentType) throws IOException {
        HttpURLConnection conn = null;
        // 判断连接协议
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
            	// 用TLS安全传输层协议
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, 
                		new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
            	@Override
                public boolean verify(String hostname, SSLSession session) {
                	// 默认都认证通过
                    return true;
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true); // 允许输入
        conn.setDoOutput(true);// 允许输出
        
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Connection", "Keep-Alive");	// 设置连接持续有效
        return conn;

    }
    
    /**
     * 请求数据放在body中请求
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    public static String  post(String path, String data) {
    	try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(path);
			post.addHeader("Content-Type", "application/json; charset=UTF-8");
			StringEntity entity = new StringEntity(data, "utf-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			return EntityUtils.toString(response.getEntity());
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * 请求数据放在body中请求
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
	public static byte[]  post(String path, byte[] data) throws Exception{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(path);
		if (data != null && data.length > 0) {
			ByteArrayEntity entity = new ByteArrayEntity(data);
			post.setEntity(entity);
		}
		HttpResponse response = client.execute(post);
		return EntityUtils.toByteArray(response.getEntity());
	}
	
	/**
	 * 请求数据放在body中请求
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String postBody(String path, Map<String, String> params) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(path);
		if (params != null && params.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
		}
		HttpResponse response = client.execute(post);
		return EntityUtils.toString(response.getEntity());
	}

    /**
     * 通过get访问 默认编码设置为UTF-8
     * @param url 连接地址
     * @param params 参数
     * @return
     * @throws IOException
     */
    public static String doGet(String url, Map<String, Object> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 通过get请求 指定编码
     * @param url 连接地址
     * @param params 参数
     * @param charset 编码
     * @return
     * @throws IOException
     */
    public static String doGet(String url, Map<String, Object> params, String charset) throws IOException {
    	// 确定连接地址不能为空
        if (StringUtils.isEmpty(url) || params == null) {
            return null;
        }
        String response = "";
        String paramUrl = buildQuery(params, charset) ;
        if (StringUtils.isNotBlank(paramUrl)) {
        	url += "?" + paramUrl;
        }
        HttpURLConnection conn = null;
        String ctype = "text/html;charset=" + charset;
        conn = getConnection(new URL(url), METHOD_GET, ctype);
        response = getResponseAsString(conn);
        return response;
    }
    
    /**
     * 通过post方法请求 默认的连接超时时间为25000ms 默认的读取超时时间为25000ms
     * @param url 请求的地址
     * @param params 参数
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> params) throws IOException {
        return doPost(url, params, CONNECTTIMEOUT, READTIMEOUT);
    }

    /**
     * 
     * 通过post方法请求数据，默认字符编码为utf-8
     * @param url 请求的url地址
     * @param params 请求的参数
     * @param connectTimeOut 请求连接过期时间
     * @param readTimeOut 请求读取过期时间
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> params, int connectTimeOut, int readTimeOut) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeOut, readTimeOut);
    }

    /**
     * 
     * 通过post方法请求数据
     * @param url 请求的url地址
     * @param params 请求的参数
     * @param charset 字符编码格式
     * @param connectTimeOut 请求连接过期时间
     * @param readTimeOut 请求读取过期时间
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> params, String charset, int connectTimeOut, int readTimeOut) throws IOException {
        HttpURLConnection conn = null;
        String response = "";
        String ctype = " text/html;charset=" + charset;
        conn = getConnection(new URL(url), METHOD_POST, ctype);
        conn.setConnectTimeout(connectTimeOut);
        conn.setReadTimeout(readTimeOut);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(buildQuery(params, charset).getBytes(charset));
        outputStream.flush();
        response = getResponseAsString(conn);
        return response;
    }
    
    /**
     * 处理请求参数
     * @param params 请求参数
     * @return 构建query
     */
    public static String buildQuery(Map<String, Object> params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, Object> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                if (sb.length() > 0) {
                	sb.append("&");
                }
            }
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (aryNotEmpty(key, value)) {
                try {
                    sb.append(key).append("=").append(URLEncoder.encode(value, charset));
                } catch (UnsupportedEncodingException e) {}
            }
        }
        System.out.println(sb.toString());
        return sb.toString();

    }
    
    public static String buildQuery2(Map<String, Object> params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Entry<String, Object> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                if (sb.length() > 0) {
                	sb.append(",");
                }
            }
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (aryNotEmpty(key, value)) {
                try {
                    sb.append("\"").append(key).append("\":").append(URLEncoder.encode(value, charset));
                } catch (UnsupportedEncodingException e) {}
            }
        }
        sb.append("}");
        System.out.println(sb.toString());
        return sb.toString();

    }
    
    /**
     * 判断字符数组，不为空
     * @param values 字符数组
     * @return true or false
     */
    public static boolean aryNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= StringUtils.isNotEmpty(value);
                if (!result) {
                    return result;
                }
            }
        }
        return result;
    }
    
    private static String getResponseAsString(HttpURLConnection conn) throws IOException {
    	// 获取连接的编码格式
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        // 判断连接是否失败
        if (es != null) {
        	// 抛出错误异常
        	String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + " : " + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        } else {
        	// 返回连接成功的数据信息
        	return getStreamAsString(conn.getInputStream(), charset);
        }

    }

    /**
     * 把流转换为字符串
     * @param input
     * @param charset
     * @return
     * @throws IOException
     */
    private static String getStreamAsString(InputStream input, String charset) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(input, charset));
            String str;
            while ((str = bf.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } finally {
            if (bf != null) {
                bf.close();
                bf = null;
            }
        }

    }
    
    /**
     * 获取字符串的编码
     * @param ctype
     * @return
     */
    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;
        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split("\\;");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("\\=");
                    if (pair.length == 2) {
                        charset = pair[1].trim();
                    }
                }
            }
        }
        return charset;
    }

	/**
	 * get
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	/**
	 * post form
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			Map<String, String> bodys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }	
	
	/**
	 * Post String
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtils.isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Post stream
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put String
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtils.isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put stream
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Delete
	 *  
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doDelete(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append(host);
    	if (!StringUtils.isBlank(path)) {
    		sbUrl.append(path);
        }
    	if (null != querys) {
    		StringBuilder sbQuery = new StringBuilder();
        	for (Map.Entry<String, String> query : querys.entrySet()) {
        		if (0 < sbQuery.length()) {
        			sbQuery.append("&");
        		}
        		if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
        			sbQuery.append(query.getValue());
                }
        		if (!StringUtils.isBlank(query.getKey())) {
        			sbQuery.append(query.getKey());
        			if (!StringUtils.isBlank(query.getValue())) {
        				sbQuery.append("=");
        				sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
        			}        			
                }
        	}
        	if (0 < sbQuery.length()) {
        		sbUrl.append("?").append(sbQuery);
        	}
        }
    	
    	return sbUrl.toString();
    }
	
	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}
		
		return httpClient;
	}
	
	private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
            	@Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                	
                }
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                	
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
        	throw new RuntimeException(ex);
        }
    }

}
