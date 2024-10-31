package ru.massalskiy.MySecondTestAppSpringBoot.model;

import lombok.Getter;

@Getter
public enum Positions {
    DEV(2.2, false),
    TESTER(1.2, false),
    DBADM(1.7, false),
    DEVOPS(2.5, false),
    HR(1.2, true),
    TL(2.6, true);

    private final boolean isManager;
    private final double positionCoefficient;

    Positions(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}
