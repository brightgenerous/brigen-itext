package com.brightgenerous.itext.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;

public class PdfWriter<T> implements IPdfWriter<T> {

    private final IPdfWriterStrategy<T> strategy;

    public PdfWriter(IPdfWriterStrategy<T> strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("The strategy must not be null.");
        }

        this.strategy = strategy;
    }

    @Override
    public void write(OutputStream outputStream, T data) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("The outputStream must not be null.");
        }

        List<IDocumentCreater<T>> creaters;
        IPagesAppender appender;
        {
            IDocumentCreaters<T> cs = strategy.getDocumentCreaters();
            creaters = cs.getDocumentCreaters();
            appender = cs.getPagesAppender();
        }
        if ((creaters == null) || creaters.isEmpty()) {
            creaters = Arrays.asList(strategy.getEmptyDocumentCreater());
        }
        List<PdfReader> readers = new ArrayList<>();
        try {
            {
                int last = 0;
                {
                    for (IDocumentCreater<T> creater : creaters) {
                        PdfReader reader = creater.create(last, data);
                        last += reader.getNumberOfPages();
                        readers.add(reader);
                    }
                }
                {
                    int start = 0;
                    List<PdfReader> tmp = new ArrayList<>();
                    for (PdfReader reader : readers) {
                        if (appender != null) {
                            reader = appender.append(last, start, reader);
                        }
                        start += reader.getNumberOfPages();
                        tmp.add(reader);
                    }
                    readers.clear();
                    readers.addAll(tmp);
                }
            }
            if (strategy.getSmartMerge()) {
                PdfCopy copy = null;
                try {
                    Document doc = new Document();
                    {
                        copy = new PdfSmartCopy(doc, outputStream);
                        copy.open();
                        doc.open();
                    }
                    for (PdfReader reader : readers) {
                        for (int i = 0; i < reader.getNumberOfPages(); i++) {
                            copy.addPage(copy.getImportedPage(reader, i + 1));
                        }
                    }
                    doc.close();
                    copy.flush();
                } finally {
                    if (copy != null) {
                        copy.close();
                    }
                }
            } else {
                PdfCopyFields copy = null;
                try {
                    copy = new PdfCopyFields(outputStream);
                    {
                        copy.open();
                    }
                    for (PdfReader reader : readers) {
                        copy.addDocument(reader);
                    }
                } finally {
                    if (copy != null) {
                        copy.close();
                    }
                }
            }
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}
