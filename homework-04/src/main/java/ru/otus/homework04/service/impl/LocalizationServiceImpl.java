package ru.otus.homework04.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework04.config.LocaleProps;
import ru.otus.homework04.service.LocalizationService;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private MessageSource messageSource;
    private final Locale currentLocale;
    private final LocaleProps localeProps;

    public LocalizationServiceImpl(LocaleProps localeProps) {
        this.localeProps = localeProps;
        this.currentLocale = localeProps.getCurrentLocale();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
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