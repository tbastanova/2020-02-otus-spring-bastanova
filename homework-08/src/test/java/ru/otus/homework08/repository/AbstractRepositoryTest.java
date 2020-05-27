package ru.otus.homework08.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.homework08.utils.RawResultPrinterImpl;


@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework08.config", "ru.otus.homework08.repository", "ru.otus.homework08.events"})
@Import(RawResultPrinterImpl.class)
public abstract class AbstractRepositoryTest {
}
