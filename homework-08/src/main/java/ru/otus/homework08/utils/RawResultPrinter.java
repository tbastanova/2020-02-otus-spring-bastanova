package ru.otus.homework08.utils;

import org.bson.Document;

public interface RawResultPrinter {
    @SuppressWarnings("unchecked")
    void prettyPrintRawResult(Document document);
}
