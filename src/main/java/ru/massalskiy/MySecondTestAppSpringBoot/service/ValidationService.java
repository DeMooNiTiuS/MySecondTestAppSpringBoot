package ru.massalskiy.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.massalskiy.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.massalskiy.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.massalskiy.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
    void isCodeValid(Request request) throws UnsupportedCodeException;
}
