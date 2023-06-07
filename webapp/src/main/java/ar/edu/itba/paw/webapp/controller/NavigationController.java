package ar.edu.itba.paw.webapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {

    private final static Logger LOGGER = LoggerFactory.getLogger(NavigationController.class);

    @RequestMapping("/create")
    public ModelAndView create(){
        LOGGER.info("Accessing create page");
        String role = UserControllerAdvice.getCurrentRole();
        if(role.equals("TRUCKER")){
            LOGGER.info("Redirecting to /trips/create");
            return new ModelAndView("forward:/trips/create");
        } else if(role.equals("PROVIDER")){
            LOGGER.info("Redirecting to /requests/create");
            return new ModelAndView("forward:/requests/create");
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/explore")
    public ModelAndView explore() {
        LOGGER.info("Accessing explore page");
        String role = UserControllerAdvice.getCurrentRole();
        if(role.equals("TRUCKER")){
            LOGGER.info("Redirecting to /requests/browse");
            return new ModelAndView("forward:/requests/browse");
        } else if(role.equals("PROVIDER")){
            LOGGER.info("Redirecting to /trips/browse");
            return new ModelAndView("forward:/trips/search");
        }
        return new ModelAndView("redirect:/");
    }

}
