package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.LocaleProps;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionsService;

@Service
public class QuestionsServiceImpl implements QuestionsService {
    private int questionsCount;
    private IOService ioService;
    private final LocalizationService localizationService;
    private QuestionsDao questionsDao;
    private LocaleProps localeProps;

    public QuestionsServiceImpl(LocalizationService localizationService, QuestionsDao questionsDao, LocaleProps localeProps, IOService ioService) {
        this.localeProps = localeProps;
        this.questionsCount = localeProps.getQuestionsCount();
        this.ioService = ioService;
        this.localizationService = localizationService;
        this.questionsDao = questionsDao;
    }

    public int ask() {
        int count = 0;
        boolean currentResult;
        Question question;

        for (int i = 1; i <= questionsCount; i++) {
            question = questionsDao.findById(String.valueOf(i));
            currentResult = (question.getAnswerText().toUpperCase().equals(ioService.getLine(question.getQuestionText()).toUpperCase()));
            if (currentResult) {
                count++;
                ioService.printLine(localizationService.getValue("answer.true"));
            } else {
                ioService.printLine(localizationService.getValue("answer.false"));
            }
        }
        return count;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }
}
