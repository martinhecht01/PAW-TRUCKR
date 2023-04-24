package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
//import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService us;

    // lo habias puesto asi por alguna razon particular Gayba?
   // @Autowired
   // private UserService us;

    @Autowired
    public UserDetailsServiceImpl(final UserService us){
        this.us = us;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = us.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }

        //TODO: implement logic to grant only required authorities
        final Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new AuthUserDetailsImpl(username, user.getPassword(), authorities);
    }



}

