package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionsDao;

public interface QuestionsService {
    int ask(QuestionsDao questionsDao);
}
