package ru.otus.homework13.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework13.exception.NoAuthorFoundException;
import ru.otus.homework13.exception.NoBookFoundException;
import ru.otus.homework13.exception.NoCategoryFoundException;
import ru.otus.homework13.exception.NoCommentFoundException;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(NoBookFoundException.class)
    public ModelAndView handleNoBookFoundException(NoBookFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Книга не найдена");
        return modelAndView;
    }

    @ExceptionHandler(NoAuthorFoundException.class)
    public ModelAndView handleNoAuthorFoundException(NoAuthorFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Автор не найден");
        return modelAndView;
    }

    @ExceptionHandler(NoCategoryFoundException.class)
    public ModelAndView handleNoCategoryFoundException(NoCategoryFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Категория не найдена");
        return modelAndView;
    }

    @ExceptionHandler(NoCommentFoundException.class)
    public ModelAndView handleNoCommentFoundException(NoCommentFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Комментарий не найден");
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Доступ запрещен");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Что-то пошло не так: " + e.getCause().toString());
        return modelAndView;
    }
}
