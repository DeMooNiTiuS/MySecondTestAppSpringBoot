package ru.massalskiy.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    /*
     * Идентификатор ответа
     */
    private String uid;
    /*
     * Идентификатор операции
     */
    private String operationUid;
    /*
     * Время создания
     */
    private String systemTime;
    /*
     * Статус
     */
    private Codes code;
    /*
     * Годовая премия
     */
    private Double annualBonus;
    /*
     * Код ошибки
     */
    private ErrorCodes errorCode;
    /*
     * Текст ошибки
     */
    private ErrorMessages errorMessage;
}