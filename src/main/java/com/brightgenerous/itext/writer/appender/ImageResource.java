package com.brightgenerous.itext.writer.appender;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

public class ImageResource implements IImageResource {

    private final String fileName;

    private final URL url;

    private final byte[] bytes;

    public ImageResource(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("The fileName must not be null.");
        }

        this.fileName = fileName;
        url = null;
        bytes = null;
    }

    public ImageResource(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("The url must not be null.");
        }

        fileName = null;
        this.url = url;
        bytes = null;
    }

    public ImageResource(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("The bytes must not be null.");
        }

        fileName = null;
        url = null;
        this.bytes = bytes;
    }

    @Override
    public Image getImage() throws BadElementException, MalformedURLException, IOException {
        Image ret;
        if (fileName != null) {
            ret = Image.getInstance(fileName);
        } else if (url != null) {
            ret = Image.getInstance(url);
        } else if (bytes != null) {
            ret = Image.getInstance(bytes);
        } else {
            throw new IllegalStateException();
        }
        return ret;
    }
}
