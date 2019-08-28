package com.glitter.working.web.http;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-28
 **/
public class BodyCachingHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private HttpServletResponse response;

    public BodyCachingHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
    }

    public byte[] getBody() {
        return byteArrayOutputStream.toByteArray();
    }


    /*将数据写入byteArrayOutputStream*/
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStreamWrapper(this.byteArrayOutputStream,this.response);
    }

    @Data
    @AllArgsConstructor
    private static class ServletOutputStreamWrapper extends ServletOutputStream {
        private ByteArrayOutputStream byteArrayOutputStream;
        private HttpServletResponse response;
        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }

        @Override
        public void write(int b) throws IOException {
            this.byteArrayOutputStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            if(!response.isCommitted()){
                ServletOutputStream outputStream = this.response.getOutputStream();
                outputStream.flush();
            }
        }
    }
}
