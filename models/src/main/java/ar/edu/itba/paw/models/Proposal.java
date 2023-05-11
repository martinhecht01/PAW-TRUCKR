package ar.edu.itba.paw.models;

public class Proposal {

    private final int proposalId;
    private final int tripId;
    private final int userId;
    private final String description;
    private final String userName;

    public Proposal(int proposalid, int tripId, int userId, String description, String userName) {
        this.tripId = tripId;
        this.userId = userId;
        this.description = description;
        this.proposalId = proposalid;
        this.userName = userName;
    }

    public int getProposalId() {
        return proposalId;
    }

    public String getUserName(){
        return userName;
    }

    public int getTripId() {
        return tripId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }
}
