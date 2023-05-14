package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorsController.class);

    @RequestMapping("/errors/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView error403() {
        LOGGER.info("Error 403. Redirecting to error page.");
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", 403);
        mv.addObject("errorMsgCode", "403ErrorCode");
        mv.setViewName("landing/error");
        return mv;
    }
}