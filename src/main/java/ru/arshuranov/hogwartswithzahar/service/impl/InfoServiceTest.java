package ru.arshuranov.hogwartswithzahar.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.arshuranov.hogwartswithzahar.service.InfoService;
@Service
@Profile("!admin")
public class InfoServiceTest implements InfoService {
    @Override
    public String getPort() {
        return "Ты еще не достоин владеть этой информацией, как станешь админом возвращайся";
    }
}
