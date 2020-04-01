package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@PropertySource("classpath:application.properties")
@Component
public class LocaleProps {
    @Value("${localeLanguage}")
    private String localeLanguage;
    private Locale currentLocale;

    @Value("${dataPath}")
    private String dataPath;

    @Value("${questionsCount}")
    private Integer questionsCount;

    public Locale getCurrentLocale() {
        if (currentLocale == null) {
            currentLocale = Locale.forLanguageTag(localeLanguage);
        }
        return currentLocale;
    }

    public String getDataPath() {
        return dataPath;
    }

    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("bundle");
        ms.setDefaultEncoding("UTF-8");
        ms.setDefaultLocale(currentLocale);
        return ms;
    }

    public Integer getQuestionsCount() {
        return questionsCount;
    }
}