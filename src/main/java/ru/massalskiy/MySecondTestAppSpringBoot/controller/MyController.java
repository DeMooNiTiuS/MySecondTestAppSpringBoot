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
import ru.massalskiy.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.massalskiy.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.massalskiy.MySecondTestAppSpringBoot.service.ValidationService;


@Slf4j
@RestController
public class MyController {

    private final ModifyResponseService modifyResponseService;
    private final ValidationService validationService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(@Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        ValidationService validationService, ModifyRequestService modifyRequestService) {
        this.modifyResponseService = modifyResponseService;
        this.validationService = validationService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("response: {}", response);

        var status = HttpStatus.OK;
        try {
            validationService.isCodeValid(request);
            validationService.isValid(bindingResult);
        } catch (UnsupportedCodeException e) {
            log.error("error: {}", e.toString());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            status = HttpStatus.BAD_REQUEST;
        } catch (ValidationFailedException e) {
            log.error("error: {}", e.toString());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            log.error("error: {}", e.toString());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        modifyResponseService.modify(response);
        modifyRequestService.modify(request);

        log.info("response: {}", response);
        return new ResponseEntity<>(modifyResponseService.modify(response), status);
    }
}