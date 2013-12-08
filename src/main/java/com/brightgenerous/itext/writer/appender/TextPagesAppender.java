package com.brightgenerous.itext.writer.appender;

import java.io.IOException;
import java.text.MessageFormat;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

public class TextPagesAppender extends PositionPagesAppender {

    private final String format;

    private final BaseFont font;

    private final float fontSize;

    private final boolean kerned;

    public TextPagesAppender(AppenderFlag flag, AppenderAlign align, AppenderVertical vertical,
            float marginX, float marginY, float paddingLeft, float paddingTop, float rotate,
            String format, BaseFont font, float fontSize, boolean kerned) {
        super(flag, align, vertical, marginX, marginY, paddingLeft, paddingTop, rotate);

        if (format == null) {
            throw new IllegalArgumentException("The format must not be null.");
        }
        if (font == null) {
            throw new IllegalArgumentException("The font must not be null.");
        }
        if (fontSize <= 0) {
            throw new IllegalArgumentException("The fontSize must not be lower equal 0.");
        }

        this.format = format;
        this.font = font;
        this.fontSize = fontSize;
        this.kerned = kerned;
    }

    @Override
    protected void appendContent(int end, int start, int pages, int page, PdfContentByte content,
            Rectangle rect) throws DocumentException, IOException {
        content.beginText();
        content.setFontAndSize(getFont(), getFontSize());
        String str = getText(end, start, pages, page);
        if (getKerned()) {
            content.showTextAlignedKerned(Element.ALIGN_CENTER, str,
                    getCenterX(rect.getWidth(), content.getEffectiveStringWidth(str, getKerned())),
                    getCenterY(rect.getHeight(), content.getLeading()), getRotate());
        } else {
            content.showTextAligned(Element.ALIGN_CENTER, str,
                    getCenterX(rect.getWidth(), content.getEffectiveStringWidth(str, getKerned())),
                    getCenterY(rect.getHeight(), content.getLeading()), getRotate());
        }
        content.endText();
    }

    protected String getFormat() {
        return format;
    }

    protected String getText(int end, int start, int pages, int page) {
        return MessageFormat.format(getFormat(), Integer.valueOf(start + page),
                Integer.valueOf(end), Integer.valueOf(start), Integer.valueOf(pages),
                Integer.valueOf(page));
    }

    protected BaseFont getFont() {
        return font;
    }

    protected float getFontSize() {
        return fontSize;
    }

    protected boolean getKerned() {
        return kerned;
    }
}
