package ar.edu.itba.paw.webapp.controller.utils;

import ar.edu.itba.paw.webapp.dto.TripDto;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class PaginationHelper {

    private PaginationHelper(){

    }

    public static void getLinks(Response.ResponseBuilder response, UriInfo uriInfo, int currentPage, int lastPage){
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", 1).build(), "first");
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", lastPage).build(), "last");
        if(currentPage != 1){
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", currentPage - 1).build(), "prev");
        }
        if(currentPage != lastPage){
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", currentPage + 1).build(), "next");
        }
    }


}
