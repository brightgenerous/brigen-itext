package com.brightgenerous.itext.writer.appender;

import com.brightgenerous.itext.writer.IPageAppender;
import com.brightgenerous.itext.writer.IPagesAppender;

public class AppenderUtils {

    private AppenderUtils() {
    }

    public static IPageAppender composite(IPageAppender... delegs) {
        return new CompositeIPageAppender(delegs);
    }

    public static IPagesAppender composite(IPagesAppender... delegs) {
        return new CompositeIPagesAppender(delegs);
    }

    public static PageAppender composite(PageAppender... delegs) {
        return new CompositePageAppender(delegs);
    }

    public static PagesAppender composite(PagesAppender... delegs) {
        return new CompositePagesAppender(delegs);
    }
}
