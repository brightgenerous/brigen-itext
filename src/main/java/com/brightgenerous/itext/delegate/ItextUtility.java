package com.brightgenerous.itext.delegate;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class ItextUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final ItextDelegater delegater;

    private static final RuntimeException rex;

    static {
        ItextDelegater tmp = null;
        boolean useful = false;
        RuntimeException ex = null;
        try {
            tmp = new ItextDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve itext");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
                ex = (RuntimeException) e;
            } else {
                ex = new RuntimeException(e);
            }
        }
        USEFUL = useful;
        delegater = tmp;
        rex = ex;
    }

    private ItextUtility() {
    }

    private static ItextDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static boolean isPdf(InputStream inputStream, byte[] password) {
        return getDelegater().isPdf(inputStream, password);
    }
}
