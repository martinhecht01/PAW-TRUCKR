package ar.edu.itba.paw.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController {
    @RequestMapping("/errors/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView error403() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", 403);
        mv.addObject("errorMsgCode", "403ErrorCode");
        mv.setViewName("landing/error");
        return mv;
    }
}