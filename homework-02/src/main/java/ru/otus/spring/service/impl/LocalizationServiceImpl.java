package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.LocalizationService;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;

    @Value("${localeLanguage}")
    private Locale currentLocale;

    public LocalizationServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getValue(String param) {
        return messageSource.getMessage(param, null, currentLocale);
    }

    @Override
    public String getValue(String param, Object[] args) {

        return messageSource.getMessage(param, args, currentLocale);
    }
}