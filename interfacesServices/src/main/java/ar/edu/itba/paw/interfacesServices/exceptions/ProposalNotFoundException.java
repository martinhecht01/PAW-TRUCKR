package ar.edu.itba.paw.interfacesServices.exceptions;

public class ProposalNotFoundException extends RuntimeException{


    public ProposalNotFoundException() {
        super("Proposal not found");
    }

}
