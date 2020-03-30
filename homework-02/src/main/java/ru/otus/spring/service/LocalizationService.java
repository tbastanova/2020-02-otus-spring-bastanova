package ru.otus.spring.service;

public interface LocalizationService {
    String getValue(String param);

    String getValue(String param, Object[] args);
}
