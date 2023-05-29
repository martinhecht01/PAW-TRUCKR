package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ResetErrorException;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(TripOrRequestNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchTrip() {
        LOGGER.info("Handling TripOrRequestNotFoundException");
        final ModelAndView view = new ModelAndView("landing/error");
        view.addObject("errorCode", 404);
        view.addObject("errorMsgCode", "404TripRequestMsg");
        view.setViewName("landing/error");
        return view;
    }

    @ExceptionHandler(ProposalNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchProposal() {
        LOGGER.info("Handling ProposalNotFoundException");
        final ModelAndView view = new ModelAndView("landing/error");
        view.addObject("errorCode", 404);
        view.addObject("errorMsgCode", "404ProposalMsg");
        view.setViewName("landing/error");
        return view;
    }

    @ExceptionHandler(ResetErrorException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView resetPassError(){
        LOGGER.info("Handling ResetErrorException");
        ModelAndView mv = new ModelAndView("landing/error");
        mv.addObject("errorMsgCode", "ResetPasswordError");
        mv.setViewName("landing/error");
        return mv;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException() {
        LOGGER.info("Handling NoHandlerFoundException");
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", 404);
        mv.addObject("errorMsgCode", "PageNotFound");
        mv.setViewName("landing/error");

        return mv;
    }

    @ExceptionHandler({RuntimeException.class, NullPointerException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerError(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", 500);
        mv.addObject("errorMsgCode", "500ErrorCode");
        mv.setViewName("landing/error");
        return mv;
    }

}