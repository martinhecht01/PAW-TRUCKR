package ar.edu.itba.paw.models;


    import javax.persistence.*;

    @Entity
    @Table(name = "proposals")
    public class Proposal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "proposal_id")
        private Integer proposalId;

        @ManyToOne
        @JoinColumn(name = "trip_id")
        private Trip trip;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "description", length = 300)
        private String description;

        // Additional property not present in the base class
        @Transient
        private String userName;

        // Constructors, getters, and setters

        public Proposal() {
            // Default constructor required by Hibernate
        }

        public Proposal(int proposalId, Trip trip, User user, String description, String userName) {
            this.proposalId = proposalId;
            this.trip = trip;
            this.user = user;
            this.description = description;
            this.userName = userName;
        }
        public Proposal(Trip trip, User user, String description) {
            this.proposalId = null;
            this.trip = trip;
            this.user = user;
            this.description = description;
            this.userName = null;
        }

        public int getProposalId() {
            return proposalId;
        }

        public void setProposalId(int proposalId) {
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
