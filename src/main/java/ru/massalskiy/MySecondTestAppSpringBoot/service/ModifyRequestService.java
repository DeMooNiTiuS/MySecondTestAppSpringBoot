package ru.massalskiy.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.massalskiy.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
