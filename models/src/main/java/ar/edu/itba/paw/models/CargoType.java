package ar.edu.itba.paw.models;


import javax.persistence.*;

@Entity
@Table(name = "cargotypes")
public class CargoType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargotypes_id_seq")
    @SequenceGenerator(sequenceName = "cargotypes_id_seq", name = "cargotypes_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int cargoTypeId;

    @Column(name = "name" , nullable = false)
    private String name;

    // Constructors, getters, and setters

    public CargoType() {
        // Default constructor required by Hibernate
    }

    public CargoType(String name){
        this.name = name;
    }

    public CargoType(int id, String name) {
        this.cargoTypeId = id;
        this.name = name;
    }

    public int getCargoTypeId() {
        return cargoTypeId;
    }

    public void setCargoTypeId(int cargoTypeId) {
        this.cargoTypeId = cargoTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
