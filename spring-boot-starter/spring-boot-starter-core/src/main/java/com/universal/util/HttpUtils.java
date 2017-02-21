package com.universal.util;

import com.universal.exception.UnexpectedHttpStatusException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class HttpUtils {

    public static final int URL_ENCODE = 1;
    public static final int JS_URL_ENCODE = 2;

    private final static int TIMEOUT = 10000;
    private final static RequestConfig DEFAULT_REQUEST_CONFIG = config(TIMEOUT);
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

    static {
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(100);
    }

    public static Map<String, String> parseQueryString(final String quereyString) {

        final Map<String, String> resultMap = new HashMap<>();

        if (!StringUtils.isBlank(quereyString)) {

            String[] blocks = StringUtils.split(quereyString, "&");

            for (String block : blocks) {

                String[] kv = StringUtils.split(block, "=");
                if (kv.length == 2) {

                    resultMap.put(kv[0], kv[1]);
                }
            }
        }

        return resultMap;
    }

    public static String generateQueryString(final Map<String, String> map) {

        return generateQueryString(map, -1);
    }

    public static String generateQueryString(final Map<String, String> map, final int urlEncodeMode) {

        String queryString = StringUtils.EMPTY;
        for (Map.Entry<String, String> entry : map.entrySet()) {

            queryString += entry.getKey() + "=";

            if (urlEncodeMode >= URL_ENCODE && StringUtils.isNotEmpty(entry.getValue())) {

                String encodedValue = StringUtils.EMPTY;

                try {
                    encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }

                if (urlEncodeMode == JS_URL_ENCODE) {
                    encodedValue = encodedValue.replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                            .replaceAll("\\%7E", "~");
                }

                queryString += encodedValue;

            } else {
                queryString += entry.getValue();
            }

            queryString += "&";
        }

        return StringUtils.removeEnd(queryString, "&");
    }

    public static String get(final String url) throws UnexpectedHttpStatusException {

        return get(url, "utf-8");
    }

    public static String get(final String url, final String charset) throws UnexpectedHttpStatusException {

        return get(url, null, charset);
    }

    public static String get(final String url, final Map<String, String> headers, final String charset) throws UnexpectedHttpStatusException {

        return get(url, headers, charset, TIMEOUT);
    }

    public static String get(final String url, final Map<String, String> headers, final String charset, final int timeoutSeconds) throws UnexpectedHttpStatusException {

        final RequestBuilder requestBuilder = RequestBuilder.get().setUri(url).setConfig(config(timeoutSeconds));

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        final HttpUriRequest httpRequest = requestBuilder.build();

        try {
            return EntityUtils.toString(execute(url, httpRequest), charset);
        } catch (ParseException | IOException e) {
            httpRequest.abort();
            logger.error("Http get request failed: " + url, e);
        }

        return StringUtils.EMPTY;
    }

    private static HttpEntity execute(final String url, final HttpUriRequest request) throws UnexpectedHttpStatusException, IOException {

        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        final CloseableHttpResponse httpResponse = httpClient.execute(request);

        final StatusLine httpStatus = httpResponse.getStatusLine();

        if (httpStatus.getStatusCode() != 200) {

            if (httpResponse != null) {
                httpResponse.close();
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }

            if (httpClient != null) {
                httpClient.close();
            }
            
            throw new UnexpectedHttpStatusException(httpStatus.getStatusCode(), url);
        }

        return httpResponse.getEntity();

    }

    private static RequestConfig config(final int timeout) {

        if (DEFAULT_REQUEST_CONFIG != null && timeout == TIMEOUT) {
            return DEFAULT_REQUEST_CONFIG;
        }

        return RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
    }

    public static String post(final String url, final Map<String, String> parameters) throws UnexpectedHttpStatusException {

        return post(url, parameters, null, TIMEOUT);
    }

    public static String post(final String url, final Map<String, String> parameters, final String data, final int timeoutSeconds) throws UnexpectedHttpStatusException {

        final RequestBuilder requestBuilder = RequestBuilder.post().setUri(url).setConfig(config(timeoutSeconds));

        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                requestBuilder.addParameter(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        if (StringUtils.isNotEmpty(data)) {
            requestBuilder.setEntity(new StringEntity(data, ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8)));
        }

        final HttpUriRequest httpRequest = requestBuilder.build();

        try {
            return EntityUtils.toString(execute(url, httpRequest));
        } catch (ParseException | IOException e) {
            httpRequest.abort();
            logger.error("Http post request failed: " + url, e);
        }

        return StringUtils.EMPTY;
    }

    public static String post(final String url, final String data, final int timeoutSeconds) throws UnexpectedHttpStatusException {

        return post(url, null, data, TIMEOUT);
    }

    public static String post(final String url, final String data) throws UnexpectedHttpStatusException {

        return post(url, data, TIMEOUT);
    }

    public static String ip(final HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        ip = request.getHeader("X-Real-IP");

        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            return request.getRemoteAddr();
        }
    }
}
