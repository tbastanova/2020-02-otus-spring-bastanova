package ru.otus.homework03.service;

import org.springframework.context.MessageSourceAware;

public interface LocalizationService extends MessageSourceAware {
    String getValue(String param);

    String getValue(String param, Object[] args);
}
