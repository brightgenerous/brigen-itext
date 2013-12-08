package com.brightgenerous.itext.writer;

import java.io.IOException;
import java.io.Serializable;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

class EmptyPageAppender implements IPageAppender, Serializable {

    private static final long serialVersionUID = -6550790798300472228L;

    @Override
    public PdfReader append(int start, PdfReader reader) throws DocumentException, IOException {
        return reader;
    }
}
