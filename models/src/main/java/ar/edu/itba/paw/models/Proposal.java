package ar.edu.itba.paw.models;

public class Proposal {

    private final int proposalid;
    private final int tripid;
    private final int userid;
    private final String description;
    private final String userName;

    public Proposal(int proposalid,int tripid, int userid, String description, String userName) {
        this.tripid = tripid;
        this.userid = userid;
        this.description = description;
        this.proposalid = proposalid;
        this.userName = userName;
    }

    public int getProposalid() {
        return proposalid;
    }

    public String getUserName(){
        return userName;
    }

    public int getTripid() {
        return tripid;
    }

    public int getUserid() {
        return userid;
    }

    public String getDescription() {
        return description;
    }
}
