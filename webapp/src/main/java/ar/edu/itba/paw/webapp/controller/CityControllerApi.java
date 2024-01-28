package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.CityService;
import ar.edu.itba.paw.interfacesServices.exceptions.CityNotFoundException;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import ar.edu.itba.paw.webapp.dto.CityDto;
import ar.edu.itba.paw.webapp.dto.TripDto;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("cities")
@Controller
public class CityControllerApi {

    private final CityService cs;

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @Context
    private UriInfo uriInfo;

    @Autowired
    public CityControllerApi(final CityService cs) {
        this.cs = cs;
    }

    @GET
    @Produces("application/vnd.city.v1+json")
    @Path("/{id}")
    public Response getCity(@PathParam("id") final int id) {
        final City city = cs.getCityById(id).orElseThrow(CityNotFoundException::new);
        Response.ResponseBuilder response = Response.ok(CityDto.fromCity(uriInfo, city));
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }

    @GET
    @Produces("application/vnd.cityList.v1+json")
    public Response getCities() {
        List<City> cityList = cs.getAllCities();
        List<CityDto> dtoList = cityList.stream().map(currifyUriInfo(CityDto::fromCity)).collect(Collectors.toList());
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<CityDto>>(dtoList){});
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }

}
