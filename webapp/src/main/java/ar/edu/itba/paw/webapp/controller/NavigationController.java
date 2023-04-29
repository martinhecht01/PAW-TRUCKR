package ar.edu.itba.paw.webapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {

    @RequestMapping("/create")
    public ModelAndView create(){
        String role = UserControllerAdvice.getCurrentRole();
        if(role.equals("TRUCKER")){
            return new ModelAndView("forward:/trips/create", "method", "GET");
        } else if(role.equals("PROVIDER")){
            return new ModelAndView("forward:/requests/create", "method", "GET");
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

}
