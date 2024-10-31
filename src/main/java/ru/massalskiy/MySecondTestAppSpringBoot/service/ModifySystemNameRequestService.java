package ru.massalskiy.MySecondTestAppSpringBoot.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.massalskiy.MySecondTestAppSpringBoot.model.Request;
import ru.massalskiy.MySecondTestAppSpringBoot.util.DateTimeUtil;
import java.util.Date;

@Service
public class ModifySystemNameRequestService implements ModifyRequestService{

    @Override
    public void modify(Request request) {
        request.setSource("Service 1");
        request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));

        var httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange("http://localhost:8084/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }

}
