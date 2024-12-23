package com.hd.hd_backend.utils;
import org.apache.commons.codec.binary.Base64;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
public class ImageUtils {
    public static String convertToBase64(String imagePath) throws IOException {
        InputStream imageInputStream = new FileInputStream(imagePath);
        byte[] imageBytes = new byte[imageInputStream.available()];
        imageInputStream.close();

        return Base64.encodeBase64String(imageBytes);
    }
}


