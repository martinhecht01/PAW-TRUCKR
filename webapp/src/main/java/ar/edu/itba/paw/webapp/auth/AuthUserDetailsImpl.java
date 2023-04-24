package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUserDetailsImpl extends User {
    public AuthUserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUserDetailsImpl(String username,
                               String password,
                               boolean enabled,
                               boolean accountNonExpired,
                               boolean credentialsNonExpired,
                               boolean accountNonLocked,
                               Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
