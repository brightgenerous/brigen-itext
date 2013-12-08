package com.brightgenerous.itext.writer.creater;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.brightgenerous.itext.writer.IResourceLoader;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public abstract class AbstractResourceLoader<T> implements IResourceLoader<T> {

    @Override
    public PdfReader load(T data) throws DocumentException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter writer = null;
        try {
            Document document = new Document();
            writer = PdfWriter.getInstance(document, bos);
            document.open();
            build(document, data);
            document.close();
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return new PdfReader(new ByteArrayInputStream(bos.toByteArray()));
    }

    protected abstract void build(Document document, T data) throws DocumentException, IOException;
}
