package com.brightgenerous.itext.writer;

import java.util.List;

public interface IDocumentCreaters<T> {

    IPagesAppender getPagesAppender();

    List<IDocumentCreater<T>> getDocumentCreaters();
}
