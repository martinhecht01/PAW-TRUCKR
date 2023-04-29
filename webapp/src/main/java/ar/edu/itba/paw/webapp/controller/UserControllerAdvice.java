package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Collection;

@ControllerAdvice
public class UserControllerAdvice {

    UserService us;

    @Autowired
    public UserControllerAdvice(final UserService us){
        this.us = us;
    }

    @ModelAttribute("currentRole")
    public static String getCurrentRole() {
        Collection<? extends GrantedAuthority> c = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (c.contains(new SimpleGrantedAuthority("ROLE_TRUCKER"))){
            return "TRUCKER";
        }
        else if (c.contains(new SimpleGrantedAuthority("ROLE_PROVIDER"))){
            return "PROVIDER";
        }
        return "";
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            AuthUserDetailsImpl userDetails1 = (AuthUserDetailsImpl) userDetails;
            return us.getUserByCuit(userDetails1.getUsername()).get();
        }
        return null;
    }

}
