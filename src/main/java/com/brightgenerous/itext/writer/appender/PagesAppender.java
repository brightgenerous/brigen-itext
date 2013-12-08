package com.brightgenerous.itext.writer.appender;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.brightgenerous.itext.writer.IPagesAppender;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public abstract class PagesAppender implements IPagesAppender {

    private final AppenderFlag flag;

    public PagesAppender(AppenderFlag flag) {
        if (flag == null) {
            throw new IllegalArgumentException("The flag must not be null.");
        }

        this.flag = flag;
    }

    @Override
    public PdfReader append(int end, int start, PdfReader reader) throws DocumentException,
            IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper stamper = null;
        try {
            stamper = new PdfStamper(reader, bos);
            int pages = reader.getNumberOfPages();
            for (int i = 0; i < pages; i++) {
                int page = i + 1;
                Rectangle rect = reader.getPageSize(page);
                {
                    PdfContentByte pcb = stamper.getOverContent(page);
                    pcb.saveState();
                    appendOverContent(end, start, pages, page, pcb, rect);
                    pcb.restoreState();
                }
                {
                    PdfContentByte pcb = stamper.getUnderContent(page);
                    pcb.saveState();
                    appendUnderContent(end, start, pages, page, pcb, rect);
                    pcb.restoreState();
                }
            }
        } finally {
            if (stamper != null) {
                stamper.close();
            }
        }
        return new PdfReader(new ByteArrayInputStream(bos.toByteArray()));
    }

    protected void appendUnderContent(int end, int start, int pages, int page,
            PdfContentByte content, Rectangle rect) throws DocumentException, IOException {
        AppenderFlag flag = getFlag();
        if (flag.equals(AppenderFlag.UNDER) || flag.equals(AppenderFlag.BOTH)) {
            appendContent(end, start, pages, page, content, rect);
        }
    }

    protected void appendOverContent(int end, int start, int pages, int page,
            PdfContentByte content, Rectangle rect) throws DocumentException, IOException {
        AppenderFlag flag = getFlag();
        if (flag.equals(AppenderFlag.OVER) || flag.equals(AppenderFlag.BOTH)) {
            appendContent(end, start, pages, page, content, rect);
        }
    }

    protected abstract void appendContent(int end, int start, int pages, int page,
            PdfContentByte content, Rectangle rect) throws DocumentException, IOException;

    protected AppenderFlag getFlag() {
        return flag;
    }
}
