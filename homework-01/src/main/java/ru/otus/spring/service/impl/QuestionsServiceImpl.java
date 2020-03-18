package ru.otus.spring.service.impl;

import org.apache.commons.csv.*;
import org.springframework.core.io.Resource;
import ru.otus.spring.service.QuestionsService;

import java.io.*;

public class QuestionsServiceImpl implements QuestionsService {
    private final Resource dataPath;

    public QuestionsServiceImpl(Resource dataPath) {
        this.dataPath = dataPath;
    }

    public int ask() throws IOException {
        int count = 0;
        boolean currentResult;
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        Reader in = new FileReader(dataPath.getFile());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String question = record.get("question");
            String answer = record.get("answer");
            System.out.println(question);
            String studentAnswer = reader.readLine();
            currentResult = (answer.toUpperCase().equals(studentAnswer.toUpperCase()));
            if (currentResult) {
                count++;
                System.out.println("Верно!");
            } else {
                System.out.println("Ошибка");
            }
        }
        return count;
    }
}
