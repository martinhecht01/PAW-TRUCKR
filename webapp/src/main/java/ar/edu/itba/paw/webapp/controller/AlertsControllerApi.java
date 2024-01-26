package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.AlertService;
import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Alert;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.AlertDto;
import ar.edu.itba.paw.webapp.form.AlertForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.function.Function;

@Path("alerts")
public class AlertsControllerApi {
    private final AlertService as;
    private final CityService cs;
    private final UserService us;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public AlertsControllerApi(final AlertService as, final CityService cs, final UserService us) {
        this.as = as;
        this.cs = cs;
        this.us = us;
    }
    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @POST
    @Consumes("application/vnd.alert.v1+json")
    @Produces("application/vnd.alert.v1+json")
    public Response createAlert(@Valid AlertForm form){
        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException :: new);
        Alert alert = as.createAlert(user, form.getOrigin(), form.getMaxWeight(), form.getMaxWeight(), (form.getFromDate() == null || form.getFromDate().isEmpty()) ? null : LocalDateTime.parse(form.getFromDate()), (form.getToDate() == null || form.getToDate().isEmpty()) ? null : LocalDateTime.parse(form.getToDate()), form.getCargoType()).get();
        return Response.created(uriInfo.getBaseUriBuilder().path("/alerts").build()).entity(AlertDto.fromAlert(uriInfo,alert)).build();
    }

    @GET
    public Response getAlert(){
        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException :: new);
        Alert alert = user.getAlert();
        return Response.ok(AlertDto.fromAlert(uriInfo,alert)).build();
    }

    @DELETE
    public Response deleteAlert(){
        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException :: new);
        as.deleteAlert(user);
        return Response.ok().build();

    }


}
