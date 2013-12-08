package com.brightgenerous.itext.delegate;

import java.io.InputStream;

interface ItextDelegater {

    boolean isPdf(InputStream inputStream, byte[] password);
}
