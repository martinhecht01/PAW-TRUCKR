package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.webapp.exception.TripNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(TripNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchTrip() {
        final ModelAndView view = new ModelAndView("landing/error");
        view.addObject("errorCode", HttpStatus.NOT_FOUND);
        view.addObject("errorMsgCode", "404TripMsg");
        view.setViewName("landing/error");
        return view;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", HttpStatus.NOT_FOUND);
        mv.addObject("errorMsgCode", "PageNotFound");
        mv.setViewName("landing/error");

        return mv;
    }

//    @ExceptionHandler({Exception.class, RuntimeException.class, NullPointerException.class})
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView internalServerError(){
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
//        mv.addObject("errorMsgCode", "500ErrorCode");
//        mv.setViewName("landing/error");
//        return mv;
//    }

}