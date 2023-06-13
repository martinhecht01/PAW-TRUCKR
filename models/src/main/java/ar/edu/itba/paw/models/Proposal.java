package ar.edu.itba.paw.models;


    import com.sun.istack.internal.Nullable;

    import javax.persistence.*;

    @Entity
    @Table(name = "proposals")
    public class Proposal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "proposal_id")
        private Integer proposalId;

        @ManyToOne
        @Nullable
        @JoinColumn(name = "trip_id")
        private Trip trip;

        @ManyToOne
        @Nullable
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "description", length = 300)
        private String description;

        @Column(name = "price")
        private Integer price;

        // Additional property not present in the base class
        @Transient
        private String userName;

        @OneToOne
        private Proposal counterProposal;

        // Constructors, getters, and setters

        public Proposal() {
            // Default constructor required by Hibernate
        }

        public Proposal(int proposalId, Trip trip, User user, String description, String userName, Integer price, Proposal counterProposal) {
            this.proposalId = proposalId;
            this.trip = trip;
            this.user = user;
            this.description = description;
            this.userName = userName;
            this.price = price;
            this.counterProposal = counterProposal;
        }
        public Proposal(Trip trip, User user, String description, Integer price) {
            this.proposalId = null;
            this.trip = trip;
            this.user = user;
            this.description = description;
            this.userName = null;
            this.price = price;
            this.counterProposal = null;
        }

        public Proposal getCounterProposal() {
            return counterProposal;
        }

        public void setCounterProposal(Proposal counterProposal) {
            this.counterProposal = counterProposal;
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

        public void setProposalId(Integer proposalId) {
            this.proposalId = proposalId;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }
