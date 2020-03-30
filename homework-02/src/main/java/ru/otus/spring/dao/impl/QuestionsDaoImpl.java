package ru.otus.spring.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ExamException;
import ru.otus.spring.service.LocalizationService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionsDaoImpl implements QuestionsDao {
    private List<Question> questions = new ArrayList<Question>();
    private LocalizationService localizationService;

    public QuestionsDaoImpl(LocalizationService localizationService) {
        this.localizationService = localizationService;

        Resource dataPath = new ClassPathResource(localizationService.getValue("dataPath"));
        Reader in = null;
        Iterable<CSVRecord> records = null;

        try {
            in = new FileReader(dataPath.getFile());
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                this.questions = this.addQuestion(this.questions, record.get("id"), record.get("question"), record.get("answer"));
            }

        } catch (FileNotFoundException e) {
            throw new ExamException(localizationService.getValue("error.fileNotFound") + dataPath);
        } catch (IOException e) {
            throw new ExamException(localizationService.getValue("error.unexpectedError"));
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Question> addQuestion(List<Question> questionList, String id, String questionText, String answerText) {
        questionList.add(new Question(id, questionText, answerText));
        return questionList;
    }

    public Question findById(String id) {

        Question questionById = questions.stream()
                .filter(question -> id.equals(question.getId()))
                .findAny()
                .orElse(null);
        return questionById;
    }
}
