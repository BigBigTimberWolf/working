package com.glitter.working.module.spring.security.config.adapter;

import com.glitter.working.module.spring.security.config.adapter.provider.AuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.List;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class CoreAuthorizeConfigManager implements AuthorizeConfigManager {

    private  List<AuthorizeConfigProvider> authorizeConfigManagers;

    public CoreAuthorizeConfigManager(List<AuthorizeConfigProvider> authorizeConfigManagers) {
        this.authorizeConfigManagers = authorizeConfigManagers;
    }

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        for (AuthorizeConfigProvider authorizeConfigProvider:authorizeConfigManagers) {
            authorizeConfigProvider.config(httpSecurity);
        }
    }
}
