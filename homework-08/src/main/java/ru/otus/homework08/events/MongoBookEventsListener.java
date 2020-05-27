package ru.otus.homework08.events;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.CategoryRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookEventsListener extends AbstractMongoEventListener<Book> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.removeAuthorsArrayElementsById(id);
        bookRepository.removeCategoriesArrayElementsById(id);
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        val book = event.getSource();
        if (book.getAuthors() != null) {
            book.getAuthors().stream().filter(e -> Objects.isNull(e.getId())).forEach(authorRepository::save);
        }
        if (book.getCategories() != null) {
            book.getCategories().stream().filter(e -> Objects.isNull(e.getId())).forEach(categoryRepository::save);
        }
    }
}