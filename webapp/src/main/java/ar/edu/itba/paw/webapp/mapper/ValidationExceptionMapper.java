package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.dto.ValidationErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Component
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ValidationErrorDto> validationErrors = exception.getConstraintViolations().stream()
                .map(ValidationErrorDto::fromValidationException)
                .peek(error -> error.setMessage(
                        messageSource.getMessage(error.getMessage(), null, LocaleContextHolder.getLocale())))
                .collect(Collectors.toList());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new GenericEntity<List<ValidationErrorDto>>(validationErrors) {})
                .build();
    }
}
