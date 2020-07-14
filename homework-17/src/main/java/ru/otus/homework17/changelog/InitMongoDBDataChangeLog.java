package ru.otus.homework17.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework17.model.Author;
import ru.otus.homework17.model.Book;
import ru.otus.homework17.model.Category;
import ru.otus.homework17.model.Comment;

import java.util.Arrays;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;
    private Author author5;
    private Author author6;

    private Category category1;
    private Category category2;
    private Category category3;
    private Category category4;
    private Category category5;

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;

    @ChangeSet(order = "000", id = "dropDB", author = "tbastanova", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "tbastanova", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        author1 = template.save(new Author("Айн Рэнд"));
        author2 = template.save(new Author("Джером Клапка Джером"));
        author3 = template.save(new Author("Джером Сэлинджер"));
        author4 = template.save(new Author("Ральф Альфер"));
        author5 = template.save(new Author("Джордж Гамов"));
        author6 = template.save(new Author("Ганс Бэт"));
    }

    @ChangeSet(order = "002", id = "initCategories", author = "tbastanova", runAlways = true)
    public void initCategories(MongoTemplate template) {
        category1 = template.save(new Category("Роман"));
        category2 = template.save(new Category("Юмор"));
        category3 = template.save(new Category("Повесть"));
        category4 = template.save(new Category("Физика"));
        category5 = template.save(new Category("Искаженное название"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "tbastanova", runAlways = true)
    public void initBooks(MongoTemplate template) {
        book1 = template.save(new Book("1", "Атлант затарил гречи", Arrays.asList(author1), Arrays.asList(category1, category5)));
        book2 = template.save(new Book("2", "Трое в лодке, нищета и собаки", Arrays.asList(author2), Arrays.asList(category2, category5)));
        book3 = template.save(new Book("3", "Над пропастью моржи", Arrays.asList(author3), Arrays.asList(category3, category5)));
        book4 = template.save(new Book("4", "Трое на четырех колесах", Arrays.asList(author2), Arrays.asList(category2)));
        book5 = template.save(new Book("5", "Происхождение химических элементов", Arrays.asList(author4, author5, author6), Arrays.asList(category4)));
    }

    @ChangeSet(order = "004", id = "initComments", author = "tbastanova", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(new Comment("1", "Комментарий 1", book1));
        template.save(new Comment("2", "Комментарий 2", book1));
        template.save(new Comment("3", "Комментарий 3", book2));
        template.save(new Comment("4", "Комментарий 4", book2));
        template.save(new Comment("5", "Комментарий 5", book2));
        template.save(new Comment("6", "Комментарий 6", book3));
        template.save(new Comment("7", "Комментарий 7", book4));
        template.save(new Comment("8", "Комментарий 8", book5));
        template.save(new Comment("9", "Комментарий 9", book5));
    }
}
