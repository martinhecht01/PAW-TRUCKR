package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    public ModelAndView register(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("landing/register");
    }

//    @RequestMapping(value = "/create", method = { RequestMethod.POST })
//    public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
//        if (errors.hasErrors()) {
//            return register(form);
//        }
//        final User u = us.createUser( "mdithurbide@itba.edu.ar", "Manuel Dithurbide", "20-43988795-9");
//        return new ModelAndView("redirect:/browseTrips");
//    }

}