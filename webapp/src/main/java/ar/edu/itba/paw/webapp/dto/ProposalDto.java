package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import javax.persistence.*;

public class ProposalDto {

    private Integer proposalId;

    private Trip trip;

    private User user;

    private String description;

    private Integer price;

    private String userName;
    private Proposal counterProposal;

    public static ProposalDto fromProposal(Proposal proposal){
        ProposalDto dto = new ProposalDto();
        dto.proposalId = proposal.getProposalId();
        dto.trip = proposal.getTrip();
        dto.user = proposal.getUser();
        dto.description = proposal.getDescription();
        dto.price = proposal.getPrice();
        dto.userName = proposal.getUserName();
        dto.counterProposal = proposal.getCounterProposal();
        return dto;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
        return counterProposal;
    }

    public void setCounterProposal(Proposal counterProposal) {
        this.counterProposal = counterProposal;
    }
}
