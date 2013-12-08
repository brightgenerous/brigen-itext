package com.brightgenerous.itext.writer.creater;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.brightgenerous.itext.writer.IDataConverter;
import com.brightgenerous.itext.writer.IDocumentCreater;
import com.brightgenerous.itext.writer.IPageAppender;
import com.brightgenerous.itext.writer.IResourceLoader;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class DocumentCreater<T> implements IDocumentCreater<T> {

    private final IResourceLoader<T> resource;

    private final IDataConverter<T> converter;

    private final IPageAppender appender;

    private final boolean flatten;

    public DocumentCreater(IResourceLoader<T> resource, IDataConverter<T> converter,
            IPageAppender appender, boolean flatten) {
        if (resource == null) {
            throw new IllegalArgumentException("The resource must not be null.");
        }

        this.resource = resource;
        this.converter = converter;
        this.appender = appender;
        this.flatten = flatten;
    }

    @Override
    public PdfReader create(int start, T data) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = null;
            try {
                {
                    PdfReader reader = resource.load(data);
                    if (appender != null) {
                        reader = appender.append(start, reader);
                    }
                    stamper = new PdfStamper(reader, bos);
                }
                if ((converter != null) && (data != null)) {
                    Map<String, String> map = converter.convert(data);
                    if ((map != null) && !map.isEmpty()) {
                        AcroFields acroFields = stamper.getAcroFields();
                        for (Entry<String, String> field : map.entrySet()) {
                            acroFields.setField(field.getKey(), field.getValue());
                        }
                    }
                }
                if (flatten) {
                    stamper.setFormFlattening(true);
                }
            } finally {
                if (stamper != null) {
                    stamper.close();
                }
            }
            return new PdfReader(new ByteArrayInputStream(bos.toByteArray()));
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}
