package ar.edu.itba.paw.services.Exceptions;

public class ProposalNotFoundException extends RuntimeException{


    public ProposalNotFoundException() {
        super("Proposal not found");
    }

}
