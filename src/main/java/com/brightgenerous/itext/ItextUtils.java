package com.brightgenerous.itext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.brightgenerous.itext.delegate.ItextUtility;

@SuppressWarnings("deprecation")
public class ItextUtils {

    public static boolean useful() {
        return ItextUtility.USEFUL;
    }

    private ItextUtils() {
    }

    public static boolean isPdf(byte[] bytes) {
        return isPdf(bytes, null);
    }

    public static boolean isPdf(byte[] bytes, byte[] password) {
        if (bytes == null) {
            throw new IllegalArgumentException("The bytes must not be null.");
        }

        return isPdf(new ByteArrayInputStream(bytes), password);
    }

    public static boolean isPdf(String fileName) {
        return isPdf(fileName, null);
    }

    public static boolean isPdf(String fileName, byte[] password) {
        if (fileName == null) {
            throw new IllegalArgumentException("The fileName must not be null.");
        }

        return isPdf(new File(fileName), password);
    }

    public static boolean isPdf(File file) {
        return isPdf(file, null);
    }

    public static boolean isPdf(File file, byte[] password) {
        if (file == null) {
            throw new IllegalArgumentException("The file must not be null.");
        }

        if (!file.exists()) {
            return false;
        }
        if (!file.canRead()) {
            return false;
        }
        if (file.isDirectory()) {
            return false;
        }

        boolean ret = false;
        try {
            ret = isPdf(new FileInputStream(file), password);
        } catch (FileNotFoundException e) {
        }
        return ret;
    }

    public static boolean isPdf(URL url) {
        return isPdf(url, null);
    }

    public static boolean isPdf(URL url, byte[] password) {
        if (url == null) {
            throw new IllegalArgumentException("The url must not be null.");
        }

        boolean ret = false;
        try (InputStream inputStream = url.openStream()) {
            ret = isPdf(inputStream, password);
        } catch (IOException e) {
        }
        return ret;
    }

    public static boolean isPdf(InputStream inputStream) {
        return isPdf(inputStream, null);
    }

    public static boolean isPdf(InputStream inputStream, byte[] password) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The inputStream must not be null.");
        }

        return ItextUtility.isPdf(inputStream, password);
    }
}
