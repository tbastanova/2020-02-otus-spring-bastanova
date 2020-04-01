package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.LocaleProps;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.impl.LocalizationServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс QuestionsDaoImpl")
class QuestionsDaoImplTest {
    private static final String ID_TRUE = "ID_TRUE";
    private static final String ID_FALSE = "ID_FALSE";
    private static final String QUESTION = "Вопрос";
    private static final String ANSWER = "Ответ";

    private LocalizationService localizationService;
    private QuestionsDao questionsDao;

    private LocaleProps localeProps;

    @BeforeEach
    public void setUp() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("bundle");
        ms.setDefaultEncoding("UTF-8");

        localeProps = Mockito.mock(LocaleProps.class);
        Mockito.when(localeProps.getDataPath()).thenReturn("test.csv");
        Mockito.when(localeProps.getCurrentLocale()).thenReturn(Locale.forLanguageTag("ru_RU"));
        Mockito.when(localeProps.getQuestionsCount()).thenReturn(5);
        Mockito.when(localeProps.getMessageSource()).thenReturn(ms);

        localizationService = new LocalizationServiceImpl(localeProps);
        questionsDao = new QuestionsDaoImpl(localizationService, localeProps);
    }

    @DisplayName("addQuestion. В список корректно добавлена запись")
    @Test
    void addQuestion() {
        List<Question> questions = new ArrayList<Question>();
        Question questionTrue = new Question(ID_TRUE, QUESTION, ANSWER);
        boolean result = false;

        questions = questionsDao.addQuestion(questions, ID_TRUE, QUESTION, ANSWER);

        for (Question question :
                questions) {
            if (question.equals(questionTrue)) {
                result = true;
            }
        }
        assertTrue(result);
    }

    @DisplayName("getQuestions. Возвращенный список не пуст")
    @Test
    void getQuestions() {
        boolean result = questionsDao.getQuestions().size() > 0;
        assertTrue(result);
    }

    @DisplayName("findById. Запись найдена")
    @Test
    void findNotNullById() {
        List<Question> questions = questionsDao.getQuestions();
        questions = questionsDao.addQuestion(questions, ID_TRUE, QUESTION, ANSWER);

        Question question = new Question(ID_TRUE, QUESTION, ANSWER);
        assertEquals(question, questionsDao.findById(ID_TRUE));
    }

    @DisplayName("findById. Запись не найдена")
    @Test
    void findNullById() {
        assertEquals(null, (Supplier<String>) questionsDao.findById(ID_FALSE));
    }


}