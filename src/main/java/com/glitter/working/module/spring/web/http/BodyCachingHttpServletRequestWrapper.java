package com.glitter.working.module.spring.web.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-28
 **/
public class BodyCachingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private ServletInputStream servletInputStream;
    private HttpServletRequest request;
    @Getter
    private byte[] body;
    public BodyCachingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request=request;
        this.servletInputStream=request.getInputStream();
        this.body=IOUtils.toByteArray(this.servletInputStream);

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.servletInputStream;
    }


    @Data
    @AllArgsConstructor
    private static class ServletInputStreamWrapper extends ServletInputStream {

        private InputStream inputStream;

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }
}
