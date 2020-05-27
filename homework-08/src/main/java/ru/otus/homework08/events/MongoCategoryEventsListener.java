package ru.otus.homework08.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.CategoryRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoCategoryEventsListener extends AbstractMongoEventListener<Category> {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Category> event) {
        super.onBeforeConvert(event);
        val category = event.getSource();
        if (category.getBooks() != null) {
            category.getBooks().stream().filter(e -> Objects.isNull(e.getId())).forEach(bookRepository::save);
        }
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Category> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        categoryRepository.removeBooksArrayElementsById(id);
    }
}
