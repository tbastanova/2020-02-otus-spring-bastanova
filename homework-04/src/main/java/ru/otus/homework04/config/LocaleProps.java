package ru.otus.homework04.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@ConfigurationProperties("locale")
@Data
public class LocaleProps {
    private String localeLanguage;
    private Locale currentLocale;
    private String dataPath;

    public Locale getCurrentLocale() {
        if (currentLocale == null) {
            currentLocale = Locale.forLanguageTag(localeLanguage);
        }
        return currentLocale;
    }
}