package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
//import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService us;

    @Autowired
    public UserDetailsServiceImpl(final UserService us){
        this.us = us;
    }

    @Override
    public UserDetails loadUserByUsername(final String cuit) throws UsernameNotFoundException {
        final User user = us.getUserByCuit(cuit).orElseThrow(UserNotFoundException::new);
        final Collection<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new AuthUserDetailsImpl(user.getCuit(), user.getPassword(), authorities);
    }



}
