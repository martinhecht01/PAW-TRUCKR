package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.User;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "passwordresets")
public class Reset {

    @Id
    @Column(name = "hash")
    private int hash;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "createdate")
    private Timestamp createDate;

    @Column(name = "completed")
    private Boolean completed;

    // Constructors, getters, and setters

    public Reset(User user, int hash, Timestamp createDate, boolean completed) {
        this.user = user;
        this.hash = hash;
        this.createDate = createDate;
        this.completed = completed;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
