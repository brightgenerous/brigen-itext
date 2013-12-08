package com.brightgenerous.itext.writer.appender;

import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

public interface IImageResource {

    Image getImage() throws BadElementException, MalformedURLException, IOException;
}
