package com.brightgenerous.pdf.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import com.brightgenerous.itext.writer.PdfWriterBuilder;
import com.brightgenerous.itext.writer.appender.AppenderAlign;
import com.brightgenerous.itext.writer.appender.AppenderBuilder;
import com.brightgenerous.itext.writer.appender.AppenderUtils;
import com.brightgenerous.itext.writer.creater.AbstractResourceLoader;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;

public class PdfWriterBuilderTest {

    @Test
    public void build() throws FileNotFoundException, IOException {
        PdfWriterBuilder<Void> builder = PdfWriterBuilder.create();
        builder.pagesAppender(AppenderUtils.composite(AppenderBuilder.create().buildPagesFooter(),
                AppenderBuilder.create().align(AppenderAlign.LEFT).format("{0} of {1}")
                        .buildPagesFooter(),
                AppenderBuilder.create().format("- - - - - - - - - -SAMPLE- - - - - - - - - -")
                        .rotate(45).buildPagesText()));
        builder.addCreater(new AbstractResourceLoader<Void>() {

            @Override
            protected void build(Document doc, Void data) throws DocumentException, IOException {
                for (int i = 0; i < 100; i++) {
                    doc.add(new Phrase("1 - " + i + "\n"));
                }
            }
        });
        builder.addCreater(new AbstractResourceLoader<Void>() {

            @Override
            protected void build(Document doc, Void data) throws DocumentException, IOException {
                doc.add(new Phrase("2 あああああ　ほげ", new Font(BaseFont.createFont("HeiseiKakuGo-W5",
                        "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED), 12)));
            }
        });
        try (OutputStream os = new FileOutputStream(
                "C:/Users/master/Desktop/PdfWriterBuilderTest_itext.pdf")) {
            builder.build().write(os, null);
        }
    }
}
