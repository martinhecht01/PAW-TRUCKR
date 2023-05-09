package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfacesServices.TripService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUserDetailsImpl;
import ar.edu.itba.paw.webapp.exception.RequestNotFoundException;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {
    @RequestMapping("/create")
    public ModelAndView create(){
        String role = UserControllerAdvice.getCurrentRole();
        if(role.equals("TRUCKER")){
            return new ModelAndView("forward:/trips/create");
        } else if(role.equals("PROVIDER")){
            return new ModelAndView("forward:/requests/create");
        }
        return new ModelAndView("redirect:/");
    }
    @RequestMapping("/explore")
    public ModelAndView creates() {
        String role = UserControllerAdvice.getCurrentRole();
        if(role.equals("TRUCKER")){
            return new ModelAndView("forward:/requests/browse");
        } else if(role.equals("PROVIDER")){
            return new ModelAndView("forward:/trips/browse");
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/active")
    public ModelAndView active(){
        String role = UserControllerAdvice.getCurrentRole();
        if(role.equals("TRUCKER")){
            return new ModelAndView("forward:/requests/active", "method", "GET");
        } else if(role.equals("PROVIDER")){
            return new ModelAndView("forward:/trips/active", "method", "GET");
        }
        return new ModelAndView("redirect:/");
    }

}
