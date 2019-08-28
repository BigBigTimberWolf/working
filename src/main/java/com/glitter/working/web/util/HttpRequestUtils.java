package com.glitter.working.web.util;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-28
 **/
public class HttpRequestUtils {


    /*判断是否是上传文件请求
    * @return true是上传 false不是上传
    * */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }
}
