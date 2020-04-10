package ru.otus.homework04.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Question")
class QuestionTest {
    private static final String ID = "ID";
    private static final String QUESTION = "Вопрос";
    private static final String ANSWER = "Ответ";
    private final Question question;

    QuestionTest() {
        this.question = new Question(ID, QUESTION, ANSWER);
    }

    @DisplayName("Id задан корректно")
    @Test
    void getId() {
        assertEquals(ID, question.getId());
    }

    @DisplayName("Вопрос задан корректно")
    @Test
    void getQuestionText() {
        assertEquals(QUESTION, question.getQuestionText());
    }

    @DisplayName("Ответ задан корректно")
    @Test
    void getAnswerText() {
        assertEquals(ANSWER, question.getAnswerText());
    }
}