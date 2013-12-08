package com.brightgenerous.itext.writer.appender;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;

public class ImagePageAppender extends PositionPageAppender {

    private final IImageResource resource;

    private final boolean inline;

    public ImagePageAppender(AppenderFlag flag, AppenderAlign align, AppenderVertical vertical,
            float marginX, float marginY, float paddingLeft, float paddingTop, float rotate,
            IImageResource resource, boolean inline) {
        super(flag, align, vertical, marginX, marginY, paddingLeft, paddingTop, rotate);

        if (resource == null) {
            throw new IllegalArgumentException("The resource must not be null.");
        }

        this.resource = resource;
        this.inline = inline;
    }

    @Override
    protected void appendContent(int start, int pages, int page, PdfContentByte content,
            Rectangle rect) throws DocumentException, IOException {
        Image image = getResource().getImage();
        if (image != null) {
            image.setRotation(getRotate());
            float width = image.getScaledWidth();
            float height = image.getScaledWidth();
            image.setAbsolutePosition(getCornerX(rect.getWidth(), width),
                    getCornerY(rect.getHeight(), height));
            content.addImage(image, getInline());
        }
    }

    protected IImageResource getResource() {
        return resource;
    }

    protected boolean getInline() {
        return inline;
    }
}
