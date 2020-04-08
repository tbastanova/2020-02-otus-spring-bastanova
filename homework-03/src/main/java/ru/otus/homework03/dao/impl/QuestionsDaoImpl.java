package ru.otus.homework03.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.config.LocaleProps;
import ru.otus.homework03.dao.QuestionsDao;
import ru.otus.homework03.domain.Question;
import ru.otus.homework03.service.ExamException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionsDaoImpl implements QuestionsDao {
    private List<Question> questions;
    private final LocaleProps localeProps;

    public QuestionsDaoImpl(LocaleProps localeProps) {
        this.localeProps = localeProps;
    }

    private void loadQuestions() {
        Reader in = null;
        Iterable<CSVRecord> records = null;
        List<Question> questions = new ArrayList<>();

        try {
            Resource dataResource = new ClassPathResource(localeProps.getDataPath());
            in = new FileReader(dataResource.getFile());
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                questions = addQuestion(questions, record.get("id"), record.get("question"), record.get("answer"));
            }
            this.questions = questions;
        } catch (IOException e) {
            throw new ExamException(e);
        }
    }

    public List<Question> getQuestions() {
        if (questions == null) {
            loadQuestions();
        }
        return questions;
    }

    public List<Question> addQuestion(List<Question> questionList, String id, String questionText, String answerText) {
        questionList.add(new Question(id, questionText, answerText));
        return questionList;
    }

    public Question findById(String id) {
        Question questionById = getQuestions().stream()
                .filter(question -> id.equals(question.getId()))
                .findAny()
                .orElse(null);
        return questionById;
    }
}
