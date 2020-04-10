package ru.otus.homework04.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework04.config.LocaleProps;
import ru.otus.homework04.domain.Student;
import ru.otus.homework04.service.ExamService;
import ru.otus.homework04.service.QuestionsService;
import ru.otus.homework04.service.StudentService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест команд shell ")
@SpringBootTest
class ApplicationShellCommandsTest {

    private static final String GREETING_PATTERN = "Добро пожаловать: %s %s";
    private static final String STUDENT_FIRSTNAME = "Вася";
    private static final String STUDENT_LASTNAME = "Пупкин";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_EXAM = "exam";
    private static final String COMMAND_EXAM_SHORT = "e";
    private static final String EXAM_COMPLITED = "Опрос завершен";

    @Autowired
    private Shell shell;

    @Autowired
    ApplicationShellCommands applicationShellCommands;

    @MockBean
    private LocaleProps localeProps;

    @MockBean
    private StudentService studentService;

    @MockBean
    private QuestionsService questionsService;

    @MockBean
    private ExamService examService;

    @BeforeEach
    void setUp() {
        Mockito.when(localeProps.getDataPath()).thenReturn("ru/test.csv");
        Mockito.when(localeProps.getCurrentLocale()).thenReturn(Locale.forLanguageTag("ru_RU"));

        Student student = new Student();
        student.setLastName(STUDENT_LASTNAME);
        student.setFirstName(STUDENT_FIRSTNAME);

        Mockito.when(studentService.register()).thenReturn(student);
        Mockito.when(questionsService.ask()).thenReturn(5);
    }

    @DisplayName("метод Login вернул ожидаемое приветствие")
    @Test
    void shouldReturnExpectedGreetingAfterLogin() {
        assertThat(applicationShellCommands.login()).isEqualTo(String.format(GREETING_PATTERN, STUDENT_FIRSTNAME, STUDENT_LASTNAME));
    }

    @DisplayName("выполнил login для полной формы shell логина")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(applicationShellCommands.login());
    }

    @DisplayName("выполнил login для короткой формы shell логина")
    @Test
    void shouldReturnExpectedGreetingAfterShortLoginCommandEvaluated() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(applicationShellCommands.login());
    }

    @DisplayName(" метод Exam вернул то что должен")
    @Test
    void shouldReturnExpectedMesageAfterExam() {
        assertThat(applicationShellCommands.exam()).isEqualTo(EXAM_COMPLITED);
    }

    @DisplayName(" должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения shell команды exam пользователь не выполнил login")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginBeforExamCommandEvaluated() {
        Object res = shell.evaluate(() -> COMMAND_EXAM);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("выполнил exam для полной формы shell exam, если ранее был выполнен login")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldRunExamWhenUserDoesLoginBeforeExamCommandEvaluated() {
        shell.evaluate(() -> COMMAND_LOGIN);
        String res = (String) shell.evaluate(() -> COMMAND_EXAM);
        assertThat(res).isEqualTo(applicationShellCommands.exam());
    }

    @DisplayName("выполнил exam для короткой формы shell exam, если ранее был выполнен login")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldRunExamWhenUserDoesLoginBeforeShortExamCommandEvaluated() {
        shell.evaluate(() -> COMMAND_LOGIN);
        String res = (String) shell.evaluate(() -> COMMAND_EXAM_SHORT);
        assertThat(res).isEqualTo(applicationShellCommands.exam());
    }
}