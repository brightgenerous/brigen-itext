package com.brightgenerous.itext.writer;

import java.io.IOException;
import java.io.Serializable;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

class EmptyPagesAppender implements IPagesAppender, Serializable {

    private static final long serialVersionUID = -5768543467458069306L;

    @Override
    public PdfReader append(int end, int start, PdfReader reader) throws DocumentException,
            IOException {
        return reader;
    }
}
