package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_imageid_seq")
    @SequenceGenerator(sequenceName = "images_imageid_seq", name = "images_imageid_seq", allocationSize = 1)
    @Column(name = "imageid")
    private int imageid;

    @Column(name = "image", columnDefinition = "BYTEA", nullable = false)
    private byte[] image;

    // Constructors, getters, and setters

    public Image() {
        // Default constructor required by Hibernate
    }

    public Image(int imageid, byte[] image) {
        this.imageid = imageid;
        this.image = image;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}


