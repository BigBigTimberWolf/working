package com.glitter.working.module.spring.security.handle;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author he peng
 */
public class SecurityAccessConfigHelper {

    public static final String PERMIT_ALL = "permitAll";
    public static final String DENY_ALL = "denyAll";
    public static final String ANONYMOUS = "anonymous";
    public static final String AUTHENTICATED = "authenticated";
    public static final String FULLY_AUTHENTICATED = "fullyAuthenticated";
    public static final String REMEMBER_ME = "rememberMe";

    private StringBuilder access = new StringBuilder();

    public SecurityAccessConfigHelper permitAll() {
        and();
        this.access.append(PERMIT_ALL);
        return this;
    }

    public SecurityAccessConfigHelper denyAll() {
        and();
        this.access.append(DENY_ALL);
        return this;
    }

    public SecurityAccessConfigHelper anonymous() {
        and();
        this.access.append(ANONYMOUS);
        return this;
    }

    public SecurityAccessConfigHelper authenticated() {
        and();
        this.access.append(AUTHENTICATED);
        return this;
    }

    public SecurityAccessConfigHelper fullyAuthenticated() {
        and();
        this.access.append(FULLY_AUTHENTICATED);
        return this;
    }

    public SecurityAccessConfigHelper rememberMe() {
        and();
        this.access.append(REMEMBER_ME);
        return this;
    }

    public SecurityAccessConfigHelper hasAnyRole(String... authorities) {
        String anyAuthorities = StringUtils.arrayToDelimitedString(authorities,
                "','ROLE_");
        and();
        this.access.append("hasAnyRole('ROLE_" + anyAuthorities + "')");
        return this;
    }

    public SecurityAccessConfigHelper hasRole(String role) {
        Assert.notNull(role, "role cannot be null");
        if (role.startsWith("ROLE_")) {
            throw new IllegalArgumentException(
                    "role should not start with 'ROLE_' since it is automatically inserted. Got '"
                            + role + "'");
        }

        and();
        this.access.append("hasRole('ROLE_" + role + "')");
        return this;
    }

    public SecurityAccessConfigHelper hasAnyAuthority(String... authorities) {
        String anyAuthorities = StringUtils.arrayToDelimitedString(authorities, "','");
        and();
        this.access.append("hasAnyAuthority('" + anyAuthorities + "')");
        return this;
    }

    public SecurityAccessConfigHelper hasAuthority(String authority) {
        and();
        this.access.append("hasAuthority('" + authority + "')");
        return this;
    }

    public SecurityAccessConfigHelper hasIpAddress(String ipAddressExpression) {
        and();
        this.access.append("hasIpAddress('" + ipAddressExpression + "')");
        return this;
    }

    public String access() {
        return this.access.toString();
    }


    private SecurityAccessConfigHelper and() {
        if (this.access.length() != 0) {
            this.access.append(" and ");
        }
        return this;
    }
}
