package com.brightgenerous.itext.writer;

import java.io.IOException;

import com.brightgenerous.itext.writer.creater.AbstractResourceLoader;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;

class EmptyResourceLoader<T> extends AbstractResourceLoader<T> {

    @Override
    protected void build(Document document, T data) throws DocumentException, IOException {
        document.add(new Phrase(" "));
    }
}
