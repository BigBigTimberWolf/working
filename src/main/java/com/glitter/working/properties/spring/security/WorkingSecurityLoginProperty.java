package com.glitter.working.properties.spring.security;

import lombok.Data;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-12
 **/
@Data
public class WorkingSecurityLoginProperty {
    /*是否开启自定义登录*/
    private boolean enable=false;
    /*登录链接*/
    private String loginUrl="/login";
    /*登录页面*/
    private String loginPage="/login/page";
    /*登录成功页面*/
    private String successForwardUrl="/";

    /*注销链接*/
    private String logoutUrl="/logout";

    /*注销成功之后跳转url*/
    private String logoutSuccess="/logout/success";
}
