package com.brightgenerous.itext.writer.appender;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

class CompositePageAppender extends PageAppender {

    private final List<PageAppender> delegs;

    public CompositePageAppender(List<PageAppender> delegs) {
        super(AppenderFlag.BOTH);

        this.delegs = (delegs == null) ? new ArrayList<PageAppender>() : delegs;
    }

    public CompositePageAppender(PageAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositePageAppender add(PageAppender deleg, PageAppender... delegs) {
        this.delegs.add(deleg);
        for (PageAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositePageAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PdfReader append(int start, PdfReader reader) throws DocumentException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper stamper = null;
        try {
            stamper = new PdfStamper(reader, bos);
            int pages = reader.getNumberOfPages();
            for (int i = 0; i < pages; i++) {
                int page = i + 1;
                Rectangle rect = reader.getPageSize(page);
                for (PageAppender deleg : delegs) {
                    {
                        PdfContentByte pcb = stamper.getOverContent(page);
                        pcb.saveState();
                        deleg.appendOverContent(start, pages, page, pcb, rect);
                        pcb.restoreState();
                    }
                    {
                        PdfContentByte pcb = stamper.getUnderContent(page);
                        pcb.saveState();
                        deleg.appendUnderContent(start, pages, page, pcb, rect);
                        pcb.restoreState();
                    }
                }
            }
        } finally {
            if (stamper != null) {
                stamper.close();
            }
        }
        return new PdfReader(new ByteArrayInputStream(bos.toByteArray()));
    }

    @Override
    protected void appendContent(int start, int pages, int page, PdfContentByte content,
            Rectangle rect) throws DocumentException, IOException {
        throw new UnsupportedOperationException();
    }
}
