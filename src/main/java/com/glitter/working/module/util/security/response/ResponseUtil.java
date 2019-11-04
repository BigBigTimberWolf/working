package com.glitter.working.module.util.security.response;

import com.alibaba.fastjson.JSON;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResponseUtil {


    /*直接返回请求*/
    public static void response(HttpServletResponse httpServletResponse, Integer status, String message) {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // httpServletResponse.addHeader("Access-Control-Expose-Headers", jwtTokenUtil.getHeartString());
        httpServletResponse.setStatus(status);
        ModelMap modelMap = generateMap(status, message);

        try {
            httpServletResponse.getWriter().write(JSON.toJSONString(modelMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*直接返回请求*/
    public static void response(HttpServletResponse httpServletResponse, HttpCode code, Object data) {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        /*添加返回的请求头，不然ajax、axios等无法获取自定义头*/
        //httpServletResponse.addHeader("Access-Control-Expose-Headers", jwtTokenUtil.getHeartString());
        httpServletResponse.setStatus(code.code());
        ModelMap modelMap = new ModelMap();
        modelMap.put("code", code.code());
        modelMap.put("message", code.reasonPhraseCN());
        modelMap.put("data", data);
        try {
            httpServletResponse.getWriter().write(JSON.toJSONString(modelMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ModelMap generateMap(Integer value, String message) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("code", value);
        modelMap.put("message", message);
        return modelMap;
    }
}
