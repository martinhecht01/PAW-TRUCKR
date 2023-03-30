package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private final UserService us;


    @Autowired
    public HelloWorldController(final UserService us){
        this.us = us;
        System.out.println(us);
    }

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("helloworld/index");
        mav.addObject("user", us.createUser("paw@itba.edu.ar", "mysecret"));
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("helloworld/register");
    }

}
