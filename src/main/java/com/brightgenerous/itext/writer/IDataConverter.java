package com.brightgenerous.itext.writer;

import java.util.Map;

public interface IDataConverter<T> {

    Map<String, String> convert(T data);
}
