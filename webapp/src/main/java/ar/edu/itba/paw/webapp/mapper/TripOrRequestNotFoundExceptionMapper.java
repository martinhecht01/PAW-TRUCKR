package ar.edu.itba.paw.webapp.mapper;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.webapp.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class TripOrRequestNotFoundExceptionMapper implements ExceptionMapper<TripOrRequestNotFoundException> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(TripOrRequestNotFoundException exception) {
        String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        ErrorDto errorDto = ErrorDto.fromErrorMessage(message);
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new GenericEntity<ErrorDto>(errorDto) {})
                .build();
    }
}