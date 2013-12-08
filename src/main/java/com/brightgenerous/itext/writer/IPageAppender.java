package com.brightgenerous.itext.writer;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

public interface IPageAppender {

    PdfReader append(int start, PdfReader reader) throws DocumentException, IOException;
}
