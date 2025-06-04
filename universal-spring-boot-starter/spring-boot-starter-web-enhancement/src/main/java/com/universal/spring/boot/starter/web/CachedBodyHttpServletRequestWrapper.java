package com.universal.spring.boot.starter.web;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CachedBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public CachedBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {

        super(request);
        cachedBody = StreamUtils.copyToByteArray(request.getInputStream());
    }

    public byte[] getCachedBody() {

        return cachedBody;
    }

    public String getBodyString() {

        return StringUtils.toEncodedString(cachedBody, Charset.forName(getCharacterEncoding()));
    }

    @Override
    public BufferedReader getReader() throws IOException {

        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(cachedBody);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {

                return inputStream.read();
            }

            @Override
            public boolean isFinished() {

                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {

                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

                throw new UnsupportedOperationException();
            }
        };
    }
}
