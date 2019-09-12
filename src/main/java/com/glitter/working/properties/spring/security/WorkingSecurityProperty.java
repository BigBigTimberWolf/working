package com.glitter.working.properties.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Data
public class WorkingSecurityProperty {

    //是否开启working security
    private boolean enable=false;

    /*security模式*/
    private String type="mvc";
    /*登录链接*/
    private String login="/login";
    /*登录页面*/
    private String loginPage="/login/page";
    /*登录成功页面*/
    private String successForwardUrl="/";

    /*注销链接*/
    private String logout="/logout";

    /*注销成功之后跳转url*/
    private String logoutSuccess="/logout/success";

    /*白名单模式white还是黑名单模式black*/
    private String mode="white";

}
