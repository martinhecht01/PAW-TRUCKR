package ar.edu.itba.paw.models;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerts_alert_id_seq")
    @SequenceGenerator(sequenceName="alerts_alert_id_seq", name = "alerts_alert_id_seq", allocationSize = 1)
    @Column(name = "alert_id")
    private Integer alertId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "maxWeight")
    private Integer maxWeight;
    @Column(name = "maxVolume")
    private Integer maxVolume;

    @Column(name = "fromDate", nullable = false)
    private Timestamp fromDate;

    @Nullable
    @Column(name = "toDate")
    private Timestamp toDate;

    public Alert() {
        /* For Hibernate */
    }
    public Alert(Integer alertId, User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime fromDate, LocalDateTime toDate) {
        this.alertId = alertId;
        this.user = user;
        this.city = city;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.fromDate = Timestamp.valueOf(fromDate);
        this.toDate = Timestamp.valueOf(toDate);
    }

    public Alert(User user, String city, Integer maxWeight, Integer maxVolume, LocalDateTime fromDate, LocalDateTime toDate) {
        this.user = user;
        this.city = city;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.fromDate = Timestamp.valueOf(fromDate);
        this.toDate = Timestamp.valueOf(toDate);
    }

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getFromDate() {
        return fromDate.toLocalDateTime();
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = Timestamp.valueOf(fromDate);
    }

    public LocalDateTime getToDate() {
        return toDate.toLocalDateTime();
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = Timestamp.valueOf(toDate);
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
