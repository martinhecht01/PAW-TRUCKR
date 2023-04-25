package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class UserController {

    private final UserService us;
    private final MailService ms;


    @Autowired
    public UserController(final UserService us, final MailService ms){
        this.us = us;
        this.ms = ms;
    }

//    @RequestMapping("/")
//    public ModelAndView landing() {
//        final ModelAndView mav = new ModelAndView("landing/index");
//        return mav;
//    }

    @ModelAttribute("currentRole")
    public String getCurrentRole() {
        Collection<? extends GrantedAuthority> c = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> cIterator = c.iterator();
        for (int i = 0; i < c.size(); i++) {
            if (cIterator.next().getAuthority().equals("PROVIDER")){
                return "PROVIDER";
            }
        }
        return "TRUCKER";
    }

    @RequestMapping("/")
    public ModelAndView landing() {
        //final AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //final User user = us.getUserByCuit(userDetails.getUsername());
        return new ModelAndView("landing/index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        final ModelAndView view = new ModelAndView("landing/login");
        return view;
    }

    @RequestMapping(value = "/createUser", method = { RequestMethod.GET })
    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form) {
        return new ModelAndView("redirect:/browseTrips");
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("userForm") final UserForm form) {
        return new ModelAndView("landing/register");
    }

    @RequestMapping(value = "/register", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return register(form);
        }
        us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getPassword());
        return new ModelAndView("redirect:/browseTrips");
    }


    @RequestMapping("/{id:\\d+}")
    public ModelAndView profile(@PathVariable("id") final long id) {
        final ModelAndView mav = new ModelAndView("user/profile");

       // mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));

        return mav;
    }

}