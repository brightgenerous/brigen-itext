package com.brightgenerous.itext.writer;

import java.io.IOException;
import java.io.OutputStream;

public interface IPdfWriter<T> {

    void write(OutputStream out, T data) throws IOException;
}
