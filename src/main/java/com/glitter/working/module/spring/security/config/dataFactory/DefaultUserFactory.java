package com.glitter.working.module.spring.security.config.dataFactory;

import com.glitter.working.module.spring.security.config.dataFactory.UserInfoFactory;
import com.glitter.working.module.spring.security.config.dataFactory.factory.UserFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.glitter.working.module.util.security.PasswordUtil.encoderPassword;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/

public class DefaultUserFactory implements UserInfoFactory {

    @Autowired
    private UserFactory userFactory;

    @Override
    public User getUser(String username) {
        User user = userFactory.getUser(username);
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        Collection<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        authorities.forEach(grantedAuthority -> {
            if(StringUtils.isNotEmpty(grantedAuthority.getAuthority())){
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+grantedAuthority.getAuthority().toUpperCase()));
            }
        });
        return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }


}
