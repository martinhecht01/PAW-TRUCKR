package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.OfferDto;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("offers")
@Component
public class OffersControllerApi {
    private final String PAGE_SIZE = "12";
    private final String PAGE = "1";

    private final UserService us;
    private final TripServiceV2 ts;


    @Context
    private UriInfo uriInfo;

    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }

    @Autowired
    public OffersControllerApi(final UserService us, TripServiceV2 ts){
        this.us = us;
        this.ts = ts;
    }

//    @POST
//    @Consumes("application/vnd.proposal.v1+json")
//    @Produces("application/vnd.proposal.v1+json")
//    public Response createOffer(@Valid AcceptForm form){
//        User user = us.getCurrentUser().orElseThrow(UserNotFoundException:: new);
//        Proposal proposal;
//        if(form.getProposalId() != null){
//            proposal = ts.createProposal(form.getTripId(), user, form.getDescription(), form.getPrice(), LocaleContextHolder.getLocale());
//        }else{
//            proposal = ts.sendCounterOffer(form.getProposalId(), user, form.getDescription(), form.getPrice());
//        }
//        return Response.created(uriInfo.getBaseUriBuilder().path("/offers/").path(String.valueOf(proposal.getProposalId())).build()).entity(OfferDto.fromProposal(uriInfo,proposal)).build();
//    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.proposal.v1+json")
    public Response getOffer(@PathParam("id") int id){
        final Proposal proposal = ts.getProposalById(id).get();
        return Response.ok(OfferDto.fromProposal(uriInfo,proposal)).build();
    }

    @GET
    @Produces("application/vnd.proposalList.v1+json")
    public Response getOffers(@QueryParam("tripId") int tripId, @QueryParam("page") @DefaultValue(PAGE) int page, @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize){
        final List<Proposal> proposalList = ts.getAllProposalsForTripId(tripId,page,pageSize);
        if(proposalList.isEmpty()){
            return Response.noContent().build();
        }

        final List<OfferDto> proposalDtos = proposalList.stream().map(currifyUriInfo(OfferDto::fromProposal)).collect(Collectors.toList());

        int maxPages = (ts.getProposalCountForTripId(tripId) / pageSize)+1; //TODO: pagination trips
        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<OfferDto>>(proposalDtos){});
        PaginationHelper.getLinks(toReturn,uriInfo,page,maxPages);

        return toReturn.build();
    }
    @PUT
    @Path("/{id}")
    public Response acceptOffer(@PathParam("id") int id){
        ts.acceptProposal(id, LocaleContextHolder.getLocale());
        return Response.ok().build();
    }

}
