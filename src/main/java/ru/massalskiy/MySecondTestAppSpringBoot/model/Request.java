package ru.massalskiy.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank
    @Length(max = 32)
    private String uid;
    /**
     * Уникальный идентификатор операции
     */
    @NotBlank
    @Length(max = 32)
    private String operationUid;
    /*
     * Тип системы
     */
    private Systems systemName;
    /*
     * Время создания запроса
     */
    @NotBlank
    private String systemTime;
    /*
     * Источник запроса
     */
    private String source;
    /*
     * Идентификатор диалога(?)
     */
    @Min(1)
    @Max(100000)
    private Integer communicationId;
    /*
     * Идентификатор шаблона (?)
     */
    private Integer templateId;
    /*
     * Код продукта
     */
    private Integer productCode;
    /*
     * Код из СМС
     */
    private Integer smsCode;

    /*
     * Должность
     */
    private Positions position;
    /*
     * Оклад
     */
    private Double salary;
    /*
     * Бонус к окладу
     */
    private Double bonus;
    /*
     * Отработано дней
     */
    private Integer workDays;

    @Override
    public String toString() {
        return String.format("{ uid:'%s', operationUid:'%s', systemName:'%s', systemTime:'%s', source:'%s', communicationId:'%d', templateId:'%d', productCode:'%d', smsCode:'%d'}",
                uid,
                operationUid,
                systemName,
                systemTime,
                source,
                communicationId,
                templateId,
                productCode,
                smsCode);
    }
}