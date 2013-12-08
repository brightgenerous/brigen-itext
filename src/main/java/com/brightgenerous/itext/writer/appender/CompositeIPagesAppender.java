package com.brightgenerous.itext.writer.appender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brightgenerous.itext.writer.IPagesAppender;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

class CompositeIPagesAppender implements IPagesAppender {

    private final List<IPagesAppender> delegs;

    public CompositeIPagesAppender(List<IPagesAppender> delegs) {
        this.delegs = (delegs == null) ? new ArrayList<IPagesAppender>() : delegs;
    }

    public CompositeIPagesAppender(IPagesAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositeIPagesAppender add(IPagesAppender deleg, IPagesAppender... delegs) {
        this.delegs.add(deleg);
        for (IPagesAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositeIPagesAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PdfReader append(int end, int start, PdfReader reader) throws DocumentException,
            IOException {
        PdfReader ret = reader;
        for (IPagesAppender deleg : delegs) {
            ret = deleg.append(end, start, ret);
        }
        return ret;
    }
}
