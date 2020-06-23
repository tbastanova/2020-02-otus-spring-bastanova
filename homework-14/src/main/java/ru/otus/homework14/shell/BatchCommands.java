package ru.otus.homework14.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ru.otus.homework14.config.JobConfig.IMPORT_BOOK_JOB_NAME;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    //http://localhost:8080/h2-console/

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() {
        Long executionId = jobOperator.start(IMPORT_BOOK_JOB_NAME, "");
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_BOOK_JOB_NAME));
    }
}
