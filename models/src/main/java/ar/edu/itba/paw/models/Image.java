package ar.edu.itba.paw.models;

public class Image {
    private final int imageid;
    private final byte[] image;

    public Image(final int imageid, final byte[] image) {
        this.imageid = imageid;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
