package ar.edu.itba.paw.interfacesServices.exceptions;

public class ProposalNotFoundException extends RuntimeException{

    public ProposalNotFoundException() {
        super("exception.ProposalNotFound");
    }

}
