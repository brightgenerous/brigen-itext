package com.brightgenerous.itext.delegate;

import java.io.IOException;
import java.io.InputStream;

import com.lowagie.text.pdf.PdfReader;

class ItextDelegaterImpl implements ItextDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(PdfReader.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public boolean isPdf(InputStream inputStream, byte[] password) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The inputStream must not be null.");
        }

        boolean ret = false;
        try (InputStream is = inputStream) {
            if (password == null) {
                new PdfReader(is);
            } else {
                new PdfReader(is, password);
            }
            ret = true;
        } catch (IOException e) {
        }
        return ret;
    }
}
