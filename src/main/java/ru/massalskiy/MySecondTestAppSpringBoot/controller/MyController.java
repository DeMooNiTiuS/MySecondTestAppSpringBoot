package ru.massalskiy.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.massalskiy.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.massalskiy.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.massalskiy.MySecondTestAppSpringBoot.model.*;
import ru.massalskiy.MySecondTestAppSpringBoot.service.AnnualBonusService;
import ru.massalskiy.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.massalskiy.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.massalskiy.MySecondTestAppSpringBoot.service.ValidationService;
import ru.massalskiy.MySecondTestAppSpringBoot.util.DateTimeUtil;
import java.util.Date;
@Slf4j
@RestController
public class MyController {
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final ValidationService validationService;
    private final AnnualBonusService annualBonusService;

    @Autowired
    public MyController(@Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        ModifyRequestService modifyRequestService,
                        ValidationService validationService,
                        AnnualBonusService annualBonusService) {
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.validationService = validationService;
        this.annualBonusService = annualBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);

        var responseBuilder = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY);
        if (request.getPosition() != null && request.getSalary() != null && request.getBonus() != null && request.getWorkDays() != null) {
            responseBuilder.annualBonus(annualBonusService.calculate(request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays()));
        }
        var response = responseBuilder.build();

        log.info("response: {}", response);

        var status = HttpStatus.OK;
        try {
            validationService.isCodeValid(request);
            validationService.isValid(bindingResult);
        } catch (UnsupportedCodeException e) {
            log.error("unsupportedError: {}", e.toString());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            status = HttpStatus.BAD_REQUEST;
        } catch (ValidationFailedException e) {
            log.error("validationError: {}", e.toString());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            log.error("unknownError: {}", e.toString());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        modifyResponseService.modify(response);
        modifyRequestService.modify(request);

        log.info("response: {}", response);
        return new ResponseEntity<>(response, status);
    }
}