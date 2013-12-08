package com.brightgenerous.itext.writer.appender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brightgenerous.itext.writer.IPageAppender;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

class CompositeIPageAppender implements IPageAppender {

    private final List<IPageAppender> delegs;

    public CompositeIPageAppender(List<IPageAppender> delegs) {
        this.delegs = (delegs == null) ? new ArrayList<IPageAppender>() : delegs;
    }

    public CompositeIPageAppender(IPageAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositeIPageAppender add(IPageAppender deleg, IPageAppender... delegs) {
        this.delegs.add(deleg);
        for (IPageAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositeIPageAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PdfReader append(int start, PdfReader reader) throws DocumentException, IOException {
        PdfReader ret = reader;
        for (IPageAppender deleg : delegs) {
            ret = deleg.append(start, ret);
        }
        return ret;
    }
}
