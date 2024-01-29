package ar.edu.itba.paw.webapp.mapper;
import ar.edu.itba.paw.interfacesServices.exceptions.CustomInternalServerErrorException;
import ar.edu.itba.paw.webapp.dto.ErrorDto;
import ar.edu.itba.paw.interfacesServices.exceptions.CuitNotAvailableException;
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
public class CustomInternalServerErrorExceptionMapper implements ExceptionMapper<CustomInternalServerErrorException> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(CustomInternalServerErrorException exception) {
        String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        ErrorDto errorDto = ErrorDto.fromErrorMessage(message);
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new GenericEntity<ErrorDto>(errorDto) {})
                .build();
    }
}
