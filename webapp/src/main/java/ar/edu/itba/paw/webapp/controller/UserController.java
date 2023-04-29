package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    }


    @RequestMapping("/")
    public ModelAndView landing() {
        return new ModelAndView("landing/index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("landing/login");
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
        us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword());
        return new ModelAndView("redirect:/browseTrips");
    }


    @RequestMapping("/{id:\\d+}")
    public ModelAndView profile(@PathVariable("id") final long id) {
        final ModelAndView mav = new ModelAndView("user/profile");

       // mav.addObject("user", us.findById(userId).orElseThrow(UserNotFoundException::new));

        return mav;
    }

}