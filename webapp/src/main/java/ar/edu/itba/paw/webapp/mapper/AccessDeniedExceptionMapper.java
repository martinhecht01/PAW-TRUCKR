//package ar.edu.itba.paw.webapp.mapper;
//import ar.edu.itba.paw.webapp.dto.ErrorDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.core.GenericEntity;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//
//@Component
//@Provider
//public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
//
//    @Autowired
//    private MessageSource messageSource;
//
//    @Override
//    public Response toResponse(AccessDeniedException exception) {
//        System.out.println("hola1");
//        String message = messageSource.getMessage("exception.AccessDenied", null, LocaleContextHolder.getLocale());
//        ErrorDto errorDto = ErrorDto.fromErrorMessage(message);
//        return Response
//                .status(Response.Status.FORBIDDEN)
//                .entity(new GenericEntity<ErrorDto>(errorDto) {})
//                .build();
//    }
//}
