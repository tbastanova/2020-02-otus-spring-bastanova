package ru.otus.homework04.service;

import org.springframework.context.MessageSourceAware;

public interface LocalizationService extends MessageSourceAware {
    String getValue(String param);

    String getValue(String param, Object[] args);
}
