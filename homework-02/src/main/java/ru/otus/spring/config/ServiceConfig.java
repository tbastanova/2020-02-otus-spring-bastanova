package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.dao.impl.QuestionsDaoImpl;
import ru.otus.spring.service.ConsoleService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionsService;
import ru.otus.spring.service.StudentService;
import ru.otus.spring.service.impl.ConsoleServiceImpl;
import ru.otus.spring.service.impl.LocalizationServiceImpl;
import ru.otus.spring.service.impl.QuestionsServiceImpl;
import ru.otus.spring.service.impl.StudentServiceImpl;

import java.util.Locale;
import java.util.Scanner;

@PropertySource("classpath:application.properties")
@Configuration
public class ServiceConfig {

    @Value("${questionsCount}")
    private Integer questionsCount;

    @Bean
    public int questionsCount() {
        return this.questionsCount;
    }

    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    ConsoleService consoleService() {
        return new ConsoleServiceImpl(scanner(), questionsCount, localizationService());
    }

    @Bean
    StudentService studentService() {
        return new StudentServiceImpl(consoleService(), messageSource(), localizationService());
    }

    @Bean
    LocalizationService localizationService() {
        return new LocalizationServiceImpl(messageSource());
    }

    @Bean
    QuestionsService questionsService() {
        return new QuestionsServiceImpl(questionsCount, consoleService(), localizationService());
    }

    @Bean
    public QuestionsDao questionsDao() {
        return new QuestionsDaoImpl(localizationService());
    }

    @Value("${localeLanguage}")
    private String localeLanguage;

    @Bean
    public String localeLanguage() {
        return this.localeLanguage;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("bundle");
        ms.setDefaultEncoding("UTF-8");
        ms.setDefaultLocale(Locale.forLanguageTag(localeLanguage));
        return ms;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
