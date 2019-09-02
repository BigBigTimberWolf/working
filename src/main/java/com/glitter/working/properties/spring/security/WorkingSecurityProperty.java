package com.glitter.working.properties.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Data
@ConfigurationProperties(prefix = "working.security")
public class WorkingSecurityProperty {

    private boolean enable=false;

    /*security模式*/
    private String type="mvc";
    /*登录链接*/
    private String login="/login";
    /*登录页面*/
    private String loginPage="/login/page";
    /*登录成功页面*/
    private String successForwardUrl="/";
    /*登录失败*/

    /*注销链接*/
    private String logout="/logout";

    /*注销成功之后跳转url*/
    private String logoutSuccess="/logout/success";
}
