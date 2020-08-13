package ru.otus.feedmysail.controller.page;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.feedmysail.exception.NoCategoryFoundException;
import ru.otus.feedmysail.exception.NoProductFoundException;
import ru.otus.feedmysail.exception.NoTeamFoundException;
import ru.otus.feedmysail.exception.NoUserFoundException;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(NoUserFoundException.class)
    public ModelAndView handleNoBookFoundException(NoUserFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Пользователь не найден");
        return modelAndView;
    }

    @ExceptionHandler(NoProductFoundException.class)
    public ModelAndView handleNoAuthorFoundException(NoProductFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Продукт не найден");
        return modelAndView;
    }

    @ExceptionHandler(NoCategoryFoundException.class)
    public ModelAndView handleNoCategoryFoundException(NoCategoryFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Категория не найдена");
        return modelAndView;
    }

    @ExceptionHandler(NoTeamFoundException.class)
    public ModelAndView handleNoCategoryFoundException(NoTeamFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Команда не найдена");
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
