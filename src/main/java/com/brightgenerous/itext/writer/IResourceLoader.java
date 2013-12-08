package com.brightgenerous.itext.writer;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

public interface IResourceLoader<T> {

    PdfReader load(T data) throws DocumentException, IOException;
}
