package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_id_seq")
    @SequenceGenerator(sequenceName = "cities_id_seq", name = "cities_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int cityId;

    @Column(name = "name", nullable = false)
    private String cityName;

    // Constructors, getters, and setters

    public City() {
        // Default constructor required by Hibernate
    }

    public City(String cityName){
        this.cityName = cityName;
    }

    public City(int id, String cityName) {
        this.cityId = id;
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String name) {
        this.cityName = name;
    }


}
