package ru.arshuranov.hogwartswithzahar.controller;

import org.springframework.web.bind.annotation.*;
import ru.arshuranov.hogwartswithzahar.dto.AvatarDTO;
import ru.arshuranov.hogwartswithzahar.service.AvatarService;

import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping
    public List<AvatarDTO> getPaginatedAvatars(@RequestParam int pageNumber, @RequestParam int pageSize) {

        return avatarService.getPaginatedAvatars(pageNumber, pageSize);
    }

    @GetMapping("/int-value")
    public Integer intValue() {
        return avatarService.intValue();
    }


}
