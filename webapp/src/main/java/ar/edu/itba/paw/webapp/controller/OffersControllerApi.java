package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.CounterOfferAlreadyExistsException;
import ar.edu.itba.paw.interfacesServices.exceptions.ProposalNotFoundException;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.OfferDto;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.OfferForm;
import ar.edu.itba.paw.webapp.form.ActionForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @POST
    @Consumes("application/vnd.offer.v1+json")
    @Produces("application/vnd.offer.v1+json")
    @PreAuthorize("@accessHandler.canCreateOffer(#form.getTripId(), #form.getParent_offer_id())")
    public Response createOffer(@Valid OfferForm form){
        User user = us.getCurrentUser().orElseThrow(UserNotFoundException:: new);
        Proposal proposal;
        try {
            proposal = ts.createProposal(form.getTripId(), user, form.getDescription(), form.getPrice(), form.getParent_offer_id(), LocaleContextHolder.getLocale());
        }catch (CounterOfferAlreadyExistsException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.created(uriInfo.getBaseUriBuilder().path("/offers/").path(String.valueOf(proposal.getProposalId())).build()).entity(OfferDto.fromProposal(uriInfo,proposal)).build();
    }

    @GET
    @Path("/{id:\\d+}")
    @Produces("application/vnd.offer.v1+json")
    @PreAuthorize("@accessHandler.canSeeOffer(#id)")
    public Response getOffer(@PathParam("id") int id){
        final Proposal proposal = ts.getProposalById(id).orElseThrow(ProposalNotFoundException::new);
        return Response.ok(OfferDto.fromProposal(uriInfo,proposal)).build();
    }

    @GET
    @Produces("application/vnd.offerList.v1+json")
    @PreAuthorize("@accessHandler.canSeeOffers(#tripId)")
    public Response getOffers(@QueryParam("tripId") Integer tripId,
                              @QueryParam("page") @DefaultValue(PAGE) int page,
                              @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize){
        final User user = us.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final List<Proposal> proposalList = ts.findOffers(tripId, user.getUserId(), page, pageSize);
        if(proposalList.isEmpty())
            return Response.noContent().build();
        final List<OfferDto> proposalDtos = proposalList.stream().map(currifyUriInfo(OfferDto::fromProposal)).collect(Collectors.toList());
        int maxPages = (ts.findOfferCount(tripId, user.getUserId()) / pageSize)+1; //TODO: pagination trips
        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<OfferDto>>(proposalDtos){});
        PaginationHelper.getLinks(toReturn, uriInfo, page, maxPages);

        return toReturn.build();
    }

    @PATCH
    @Path("/{id:\\d+}")
    @PreAuthorize("@accessHandler.canActOnOffer(#id)")
    public Response actOnOffer(@PathParam("id") int id, @Valid ActionForm form){
        ts.actOnOffer(id, form.getAction(), LocaleContextHolder.getLocale());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id:\\d+}")
    @PreAuthorize("@accessHandler.isOfferOwner(#id)")
    public Response deleteOffer(@PathParam("id") int id){
        ts.deleteOffer(id);
        return Response.ok().build();
    }

}
