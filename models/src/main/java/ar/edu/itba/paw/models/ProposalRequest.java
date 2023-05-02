package ar.edu.itba.paw.models;

public class ProposalRequest {
        private final int proposalid;
        private final int requestid;
        private final int userid;
        private final String description;
        private final String userName;

        public ProposalRequest(int proposalid,int requestid, int userid, String description, String userName) {
            this.requestid = requestid;
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

        public int getRequestid() {
            return requestid;
        }

        public int getUserid() {
            return userid;
        }

        public String getDescription() {
            return description;
        }
}
