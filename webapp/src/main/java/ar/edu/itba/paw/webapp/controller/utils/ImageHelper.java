package ar.edu.itba.paw.webapp.controller.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//https://www.baeldung.com/java-resize-image

public enum ImageHelper {
    FULL(1, 1),
    SQUARE(500,500);

    private final int width;
    private final int height;

    ImageHelper(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public byte[] resizeImage(byte[] image) throws IOException {
        if (image == null || this.name().equals("FULL")) {
            return image;
        }

        InputStream inputStream = new ByteArrayInputStream(image);
        BufferedImage originalBufferedImage = ImageIO.read(inputStream);

        Image resultingImage = originalBufferedImage.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);

        BufferedImage outputImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        outputImage.createGraphics().drawImage(resultingImage, 0, 0, this.width, this.height, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", outputStream);

        return outputStream.toByteArray();
    }

}