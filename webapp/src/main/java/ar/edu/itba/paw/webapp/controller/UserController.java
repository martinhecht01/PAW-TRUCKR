package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserService us;


    @Autowired
    public UserController(final UserService us){
        this.us = us;
        System.out.println(us);
    }

    @RequestMapping("/")
    public ModelAndView landing() {
        final ModelAndView mav = new ModelAndView("landing/index");
        mav.addObject("user", us.createUser("mdithurbide@itba.edu.ar", "Manuel Dithurbide", "20-43988795-9"));
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("landing/register");
    }

}