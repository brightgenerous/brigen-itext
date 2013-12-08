package com.brightgenerous.itext.writer;

public interface IPdfWriterStrategy<T> {

    IDocumentCreaters<T> getDocumentCreaters();

    IDocumentCreater<T> getEmptyDocumentCreater();

    boolean getSmartMerge();
}
