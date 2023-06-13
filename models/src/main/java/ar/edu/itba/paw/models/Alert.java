package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(name = "cities", nullable = false)
    private List<String> cities;

    @Column(name = "maxWeight")
    private Integer maxWeight;
    @Column(name = "maxWeight")
    private Integer maxVolume;

    @Column(name = "from", nullable = false)
    private Timestamp from;

    @Column(name = "to")
    private Timestamp to;

    public Alert() {
        /* For Hibernate */
    }
    public Alert(User user, List<String> cities, Integer maxWeight, Integer maxVolume, LocalDateTime from, LocalDateTime to) {
        this.user = user;
        this.cities = cities;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.from = Timestamp.valueOf(from);
        this.to = Timestamp.valueOf(to);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public LocalDateTime getFrom() {
        return from.toLocalDateTime();
    }

    public void setFrom(LocalDateTime from) {
        this.from = Timestamp.valueOf(from);
    }

    public LocalDateTime getTo() {
        return to.toLocalDateTime();
    }

    public void setTo(LocalDateTime to) {
        this.to = Timestamp.valueOf(to);
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Integer maxVolume) {
        this.maxVolume = maxVolume;
    }
}
