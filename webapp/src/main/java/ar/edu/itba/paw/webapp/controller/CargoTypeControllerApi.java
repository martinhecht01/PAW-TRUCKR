package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.CargoTypeService;
import ar.edu.itba.paw.interfacesServices.exceptions.CargoTypeNotFoundException;
import ar.edu.itba.paw.models.CargoType;
import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import ar.edu.itba.paw.webapp.dto.CargoTypeDto;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

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

@Path("cargoTypes")
@Component
public class CargoTypeControllerApi {

    private final CargoTypeService cs;

    private final MessageSource messageSource;

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @Context
    private UriInfo uriInfo;

    @Autowired
    public CargoTypeControllerApi(CargoTypeService cs, MessageSource messageSource) {
        this.cs = cs;
        this.messageSource = messageSource;
    }

    @GET
    @Path("/{id:\\d+}")
    @Produces("application/vnd.cargoType.v1+json")
    public Response getCargoTypeById(@PathParam("id") final int id) {
        final CargoType cargoType = cs.getCargoTypeById(id).orElseThrow(CargoTypeNotFoundException::new);
//        cargoType.setName(getLocalCargoType(cargoType));
        Response.ResponseBuilder response = Response.ok(CargoTypeDto.fromCargoType(uriInfo, cargoType));
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }

    @GET
    @Produces("application/vnd.cargoTypeList.v1+json")
    public Response getCargoTypes() {
        List<CargoType> cargoTypeList = cs.getAllCargoTypes();
//        cargoTypeList.forEach(cargoType -> cargoType.setName(getLocalCargoType(cargoType)));
        List<CargoTypeDto> dtoList = cargoTypeList.stream().map(currifyUriInfo(CargoTypeDto::fromCargoType)).collect(Collectors.toList());
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<CargoTypeDto>>(dtoList){});
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }

    private String getLocalCargoType(CargoType cargoType){
        return messageSource.getMessage(cargoType.getName(), null, LocaleContextHolder.getLocale());
    }

}
