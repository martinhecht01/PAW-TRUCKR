package ar.edu.itba.paw.webapp.controller.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//https://www.baeldung.com/java-resize-image

public enum ImageHelper {
    // Using INSTAGRAM sizes
    FULL(1, 1),
    PROFILE(320,320),
    SQUARE(1080, 1080);

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

        int originalWidth = originalBufferedImage.getWidth();
        int originalHeight = originalBufferedImage.getHeight();

        // Calcular las nuevas dimensiones manteniendo el aspect ratio
        int newWidth, newHeight;
        double aspectRatio = (double) originalWidth / originalHeight;

        if (aspectRatio > 1) {
            newWidth = this.width;
            newHeight = (int) (this.width / aspectRatio);
        } else {
            newWidth = (int) (this.height * aspectRatio);
            newHeight = this.height;
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(originalBufferedImage, 0, 0, newWidth, newHeight, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);

        return outputStream.toByteArray();
    }

}