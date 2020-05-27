package ru.otus.homework08.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoAuthorEventsListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Author> event) {
        super.onBeforeConvert(event);
        val author = event.getSource();
        if (author.getBooks() != null) {
            author.getBooks().stream().filter(e -> Objects.isNull(e.getId())).forEach(bookRepository::save);
        }
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        authorRepository.removeBooksArrayElementsById(id);
    }
}
