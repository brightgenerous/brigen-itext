package com.brightgenerous.itext.writer.creater;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import com.brightgenerous.itext.writer.IResourceLoader;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

public class ResourceLoader<T> implements IResourceLoader<T>, Serializable {

    private static final long serialVersionUID = 319356142238190647L;

    private final String fileName;

    private final URL url;

    private final byte[] bytes;

    public ResourceLoader(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("The fileName must not be null.");
        }

        this.fileName = fileName;
        url = null;
        bytes = null;
    }

    public ResourceLoader(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("The url must not be null.");
        }

        fileName = null;
        this.url = url;
        bytes = null;
    }

    public ResourceLoader(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("The bytes must not be null.");
        }

        fileName = null;
        url = null;
        this.bytes = bytes;
    }

    @Override
    public PdfReader load(T data) throws DocumentException, IOException {
        PdfReader ret;
        if (fileName != null) {
            ret = new PdfReader(fileName);
        } else if (url != null) {
            ret = new PdfReader(url);
        } else if (bytes != null) {
            ret = new PdfReader(bytes);
        } else {
            throw new IllegalStateException();
        }
        return ret;
    }
}
