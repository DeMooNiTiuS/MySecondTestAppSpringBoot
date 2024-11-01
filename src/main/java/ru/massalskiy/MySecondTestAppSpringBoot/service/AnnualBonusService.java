package ru.massalskiy.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.massalskiy.MySecondTestAppSpringBoot.model.Positions;

@Service
public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
}
