package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ConsoleService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionsService;

@Service
public class QuestionsServiceImpl implements QuestionsService {
    private int questionsCount;
    private ConsoleService consoleService;
    private final LocalizationService localizationService;

    public QuestionsServiceImpl(int questionsCount, ConsoleService consoleService, LocalizationService localizationService) {
        this.questionsCount = questionsCount;
        this.consoleService = consoleService;
        this.localizationService = localizationService;
    }

    public int ask(QuestionsDao questionsDao) {
        int count = 0;
        boolean currentResult;
        Question question;

        for (int i = 1; i <= questionsCount; i++) {
            question = questionsDao.findById(String.valueOf(i));
            currentResult = (question.getAnswerText().toUpperCase().equals(consoleService.getLine(question.getQuestionText()).toUpperCase()));
            if (currentResult) {
                count++;
                System.out.println(localizationService.getValue("answer.true"));
            } else {
                System.out.println(localizationService.getValue("answer.false"));
            }
        }
        return count;
    }
}
