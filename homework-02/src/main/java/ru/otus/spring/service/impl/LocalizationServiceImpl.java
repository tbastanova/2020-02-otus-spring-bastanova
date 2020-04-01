package ru.otus.spring.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.LocaleProps;
import ru.otus.spring.service.LocalizationService;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;
    private final Locale currentLocale;
    private final LocaleProps localeProps;

    public LocalizationServiceImpl(LocaleProps localeProps) {
        this.localeProps = localeProps;
        this.currentLocale = localeProps.getCurrentLocale();
        this.messageSource = localeProps.getMessageSource();
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