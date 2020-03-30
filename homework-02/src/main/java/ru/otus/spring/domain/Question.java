package ru.otus.spring.domain;

import java.util.Objects;

public class Question {
    private String id;
    private String questionText;
    private String answerText;

    public Question(String id, String questionText, String answerText) {
        this.id = id;
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(questionText, question.questionText) &&
                Objects.equals(answerText, question.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText, answerText);
    }
}
