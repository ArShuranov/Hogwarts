package ru.arshuranov.hogwartswithzahar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arshuranov.hogwartswithzahar.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/getPort")
    public String getPort() {
        return infoService.getPort();
    }
}
