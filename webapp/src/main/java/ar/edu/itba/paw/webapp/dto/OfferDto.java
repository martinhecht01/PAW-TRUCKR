package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Proposal;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class OfferDto {

    private Integer proposalId;
    private URI self;
    private URI trip;
    private URI user;
    private String description;
    private Integer price;
    private String userName;
    private Proposal counterOffer;

    public static OfferDto fromProposal(final UriInfo uriInfo, Proposal proposal){
        OfferDto dto = new OfferDto();
        dto.proposalId = proposal.getProposalId();
        dto.self = uriInfo.getBaseUriBuilder().path("/offers/").path(String.valueOf(proposal.getProposalId())).build();
        dto.trip = uriInfo.getBaseUriBuilder().path("/trips/").path(String.valueOf(proposal.getTrip().getTripId())).build();
        dto.user = uriInfo.getBaseUriBuilder().path("/users/").path(String.valueOf(proposal.getUser().getUserId())).build();
        dto.description = proposal.getDescription();
        dto.price = proposal.getPrice();
        dto.userName = proposal.getUserName();
        dto.counterOffer = proposal.getCounterProposal();
        return dto;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    public URI getTrip() {
        return trip;
    }

    public void setTrip(URI trip) {
        this.trip = trip;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Proposal getCounterProposal() {
        return counterOffer;
    }

    public void setCounterProposal(Proposal counterProposal) {
        this.counterOffer = counterProposal;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}