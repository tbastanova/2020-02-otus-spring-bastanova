package ru.otus.homework03.service.impl;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.otus.homework03.config.DataProps;
import ru.otus.homework03.dao.QuestionsDao;
import ru.otus.homework03.domain.Question;
import ru.otus.homework03.service.IOService;
import ru.otus.homework03.service.LocalizationService;
import ru.otus.homework03.service.QuestionsService;

@EnableConfigurationProperties(DataProps.class)
@Service
public class QuestionsServiceImpl implements QuestionsService {
    private final int questionsCount;
    private final IOService ioService;
    private final LocalizationService localizationService;
    private final QuestionsDao questionsDao;

    public QuestionsServiceImpl(LocalizationService localizationService, QuestionsDao questionsDao, DataProps dataProps, IOService ioService) {
        this.questionsCount = dataProps.getQuestionsCount();
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
