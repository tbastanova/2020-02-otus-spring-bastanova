package ru.otus.homework13.service.impl;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.otus.homework13.exception.NoBookFoundException;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.model.Category;
import ru.otus.homework13.repository.BookRepositoryJpa;
import ru.otus.homework13.service.AuthorService;
import ru.otus.homework13.service.BookService;
import ru.otus.homework13.service.CategoryService;
import ru.otus.homework13.service.CommentService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final MutableAclService mutableAclService;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa, AuthorService authorService, CategoryService categoryService, CommentService commentService, MutableAclService mutableAclService) {
        this.bookRepositoryJpa = bookRepositoryJpa;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.mutableAclService = mutableAclService;
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> findAll() {
        return bookRepositoryJpa.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @Override
    public Book findById(long id) {
        return bookRepositoryJpa.findById(id).orElseThrow(() -> new NoBookFoundException(new Throwable()));
    }

    @PreAuthorize("hasPermission(#book, 'WRITE') or hasRole('ROLE_ADMIN')")
    @Override
    public Book save(Book book) {
        if (book.getId() != 0) {
            Book repBook = bookRepositoryJpa.findById(book.getId()).orElseThrow(() -> new NoBookFoundException(new Throwable()));
            if (book.getAuthors() == null) {
                book.setAuthors(repBook.getAuthors());
            }
            if (book.getCategories() == null) {
                book.setCategories(repBook.getCategories());
            }
            bookRepositoryJpa.save(book);
        } else {
            bookRepositoryJpa.save(book);
            addAcl(book);
        }
        return book;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteById(long bookId) {
        bookRepositoryJpa.deleteById(bookId);
    }

    @Override
    @Transactional
    public Book setBookAuthor(long bookId, long authorId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorService.findById(authorId);
        List<Author> authors = book.getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
            book.setAuthors(authors);
            bookRepositoryJpa.save(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookAuthor(long bookId, long authorId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorService.findById(authorId);
        book.getAuthors().remove(author);
        bookRepositoryJpa.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book setBookCategory(long bookId, long categoryId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryService.findById(categoryId);
        List<Category> categories = book.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            book.setCategories(categories);
            bookRepositoryJpa.save(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookCategory(long bookId, long categoryId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryService.findById(categoryId);
        book.getCategories().remove(category);
        bookRepositoryJpa.save(book);
        return book;
    }

    @Override
    public Model getEditBookModel(long bookId, Model model) {
        model.addAttribute("book", findById(bookId));
        model.addAttribute("comments", commentService.findByBook_Id(bookId));
        return model;
    }

    protected void addAcl(Book book) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectIdentity oid = new ObjectIdentityImpl(Book.class, book.getId());

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(new PrincipalSid(authentication));
        acl.insertAce(0, BasePermission.READ, new GrantedAuthoritySid("ROLE_USER"), true);
        acl.insertAce(0, BasePermission.WRITE, new GrantedAuthoritySid("ROLE_EDITOR"), true);
        mutableAclService.updateAcl(acl);
    }
}
