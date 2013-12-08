package com.brightgenerous.itext.writer;

public class PdfWriterStrategy<T> implements IPdfWriterStrategy<T> {

    private final IDocumentCreaters<T> documentCreaters;

    private final IDocumentCreater<T> emptyDocumentCreater;

    private final boolean smartMerge;

    public PdfWriterStrategy(IDocumentCreaters<T> documentCreaters,
            IDocumentCreater<T> emptyDocumentCreater, boolean smartMerge) {
        if (documentCreaters == null) {
            throw new IllegalArgumentException("The documentCreaters must not be null.");
        }
        if (emptyDocumentCreater == null) {
            throw new IllegalArgumentException("The emptyDocumentCreater must not be null.");
        }

        this.documentCreaters = documentCreaters;
        this.emptyDocumentCreater = emptyDocumentCreater;
        this.smartMerge = smartMerge;
    }

    @Override
    public IDocumentCreaters<T> getDocumentCreaters() {
        return documentCreaters;
    }

    @Override
    public IDocumentCreater<T> getEmptyDocumentCreater() {
        return emptyDocumentCreater;
    }

    @Override
    public boolean getSmartMerge() {
        return smartMerge;
    }
}
