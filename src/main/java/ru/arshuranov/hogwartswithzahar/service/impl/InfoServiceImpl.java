package ru.arshuranov.hogwartswithzahar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.arshuranov.hogwartswithzahar.service.InfoService;

//testing application.properties returns different results depending on the user (admin || !admin)
@Service
@Profile("admin")
public class InfoServiceImpl implements InfoService {
    Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Value("${server.port}")
    private String serverPort;

    @Override
    public String getPort() {
        logger.debug("Получаем порт {}", serverPort);
        return serverPort;
    }
}
