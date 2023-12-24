package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.function.Function;

@Path("users")
@Component
public class UserControllerApi {
    private final UserService us;
    private final ImageService is;
    private final TripServiceV2 ts;
    private final ReviewService revs;

    private final String PAGE_SIZE = "12";
    private final String PAGE = "1";

    @Context
    private UriInfo uriInfo;

    @Autowired
    public UserControllerApi(final UserService us, ImageService is, ReviewService revs, TripServiceV2 ts){
        this.us = us;
        this.is = is;
        this.revs = revs;
        this.ts = ts;
    }

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }


    @POST
    @Consumes("application/vnd.user.v1+json")
    @Produces("application/vnd.user.v1+json")
    public Response createUser(@Valid UserForm form){
        final User user = us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword(), LocaleContextHolder.getLocale());
        return Response.created(uriInfo.getBaseUriBuilder().path("/users/").path(String.valueOf(user.getUserId())).build()).entity(UserDto.fromUser(uriInfo, user)).build();
    }

    @GET
    @Produces("application/vnd.user.v1+json")
    @Path("/{id}")
    public Response getUser(@PathParam("id") final Integer id){
        User user = us.getUserById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserDto.fromUser(uriInfo, user)).build();
    }



}