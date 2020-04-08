package ru.otus.homework03.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework03.config.LocaleProps;
import ru.otus.homework03.dao.QuestionsDao;
import ru.otus.homework03.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс QuestionsDaoImpl")
@SpringBootTest
class QuestionsDaoImplTest {
    private static final String ID_TRUE = "ID_TRUE";
    private static final String ID_FALSE = "ID_FALSE";
    private static final String QUESTION = "Вопрос";
    private static final String ANSWER = "Ответ";

    @Configuration
    static class NestedConfiguration {

        @MockBean
        private LocaleProps localeProps;

        @Bean
        QuestionsDao questionsDao() {
            return new QuestionsDaoImpl(localeProps);
        }
    }

    @Autowired
    private QuestionsDao questionsDao;

    @Autowired
    private LocaleProps localeProps;

    @BeforeEach
    void setUp() {
        Mockito.when(localeProps.getDataPath()).thenReturn("ru/test.csv");
        Mockito.when(localeProps.getCurrentLocale()).thenReturn(Locale.forLanguageTag("ru_RU"));
    }

    @DisplayName("addQuestion. В список корректно добавлена запись")
    @Test
    void addQuestion() {
        List<Question> questions = new ArrayList<>();
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