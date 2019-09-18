package com.glitter.working.module.spring.web.filter;

import com.glitter.working.module.spring.web.http.BodyCachingHttpServletRequestWrapper;
import com.glitter.working.module.spring.web.http.BodyCachingHttpServletResponseWrapper;
import com.glitter.working.module.spring.web.util.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-28
 **/
@Slf4j
public class DebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        BodyCachingHttpServletResponseWrapper responseWrapper = new BodyCachingHttpServletResponseWrapper(httpServletResponse);
        try {

                 beforeRequest(httpServletRequest);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {

                afterRequest(httpServletRequest,responseWrapper);
        }
    }

    void beforeRequest(HttpServletRequest request) {
        log.info(requestLogMessage(request));
    }
    void afterRequest(HttpServletRequest request , BodyCachingHttpServletResponseWrapper response){
        log.info(responseLogMessage(request,response));
    }

    String responseLogMessage(HttpServletRequest request , BodyCachingHttpServletResponseWrapper response){
        /*获取HeaderNames*/
        Map<String , String> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        if (CollectionUtils.isNotEmpty(headerNames)) {
            for (String headerName : headerNames) {
                headers.put(headerName , response.getHeader(headerName));
            }
        }

        /*获取文件上传信息*/
        String payload=getResponsePayload(response);

        /*产生Message*/
        StringBuffer logMessage = new StringBuffer().append("\r\n******************************HTTP Response******************************\r\n")
                .append("Request URL : ").append(request.getRequestURL()).append("\r\n")
                .append("Http Status : ").append(response.getStatus()).append("\r\n")
                .append("Session ID : ").append(request.getSession(false).getId()).append("\r\n")
                .append("Content Type : ").append(response.getContentType()).append("\r\n")
                .append("Headers : ").append(headers).append("\r\n");

        if(Strings.isNotBlank(payload))
            logMessage.append("Payload : ").append(payload);
        return logMessage.toString();
    }

    String requestLogMessage(HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        /*获得参数*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> requestParameter = new HashMap<>();
        if (Objects.nonNull(parameterMap)) {
            parameterMap.entrySet().forEach(parameter -> requestParameter.put(parameter.getKey(), Arrays.deepToString(parameter.getValue())));
        }

        /*获得文件上传信息*/
        String requestPayload = getRequestPayload(request);

        /*获得cookie*/
        Cookie[] cookies = request.getCookies();
        List<String> cookieMessage = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(cookies)) {
            Arrays.stream(cookies).forEach(cookie -> cookieMessage.add(cookie.getName() + " = " + cookie.getValue()));
        }

        /*产生Message*/
        StringBuffer logMessage = new StringBuffer().append("\r\n******************************HTTP Request******************************\r\n")
                .append("Request URL : ").append(request.getRequestURL()).append("\r\n")
                .append("Method : ").append(serverHttpRequest.getMethod()).append("\r\n")
                .append("Session ID : ").append(request.getSession(false).getId()).append("\r\n")
                .append("Client IP : ").append(serverHttpRequest.getRemoteAddress()).append("\r\n")
                .append("Headers : ").append(serverHttpRequest.getHeaders()).append("\r\n")
                .append("Cookie : ").append(cookieMessage).append("\r\n");
        if(requestParameter.size()>0)
            logMessage.append("Parameter : ").append(requestParameter).append("\r\n");
        if(Strings.isNotBlank(request.getContentType()))
            logMessage.append("Content Type : ").append(request.getContentType()).append("\r\n");
        if(Strings.isNotBlank(requestPayload))
            logMessage.append("Payload : ").append(requestPayload);
        return logMessage.toString();
    }

    private String getRequestPayload(HttpServletRequest request) {
        try {
            if (HttpRequestUtils.isMultipart(request)) {
                BodyCachingHttpServletRequestWrapper readableRequest = new BodyCachingHttpServletRequestWrapper(request);
                return StringUtils.newStringUtf8(readableRequest.getBody());
            } else {
                return "";
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private String getResponsePayload(BodyCachingHttpServletResponseWrapper responseWrapper) {
        return StringUtils.newString(responseWrapper.getBody(), responseWrapper.getCharacterEncoding());
    }

}
