package ru.otus.homework04.dao;

import ru.otus.homework04.domain.Question;

import java.util.List;

public interface QuestionsDao {
    Question findById(String id);

    List<Question> getQuestions();

    List<Question> addQuestion(List<Question> questionList, String id, String questionText, String answerText);
}
