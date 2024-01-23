package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Autowired
    private MessageSource messageSource;


    public BadRequestExceptionMapper(){

    }
    @Override
    public Response toResponse(BadRequestException e) {
        String message = messageSource.getMessage("exception.BadRequest", null, LocaleContextHolder.getLocale());
        ErrorDto errorDto = ErrorDto.fromErrorMessage(message);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new GenericEntity<ErrorDto>(errorDto) {})
                .build();
    }
}