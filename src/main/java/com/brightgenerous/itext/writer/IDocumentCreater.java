package com.brightgenerous.itext.writer;

import java.io.IOException;

import com.lowagie.text.pdf.PdfReader;

public interface IDocumentCreater<T> {

    PdfReader create(int start, T data) throws IOException;
}
