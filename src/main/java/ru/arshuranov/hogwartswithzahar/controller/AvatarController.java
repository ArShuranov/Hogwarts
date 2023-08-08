package ru.arshuranov.hogwartswithzahar.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arshuranov.hogwartswithzahar.model.Avatar;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping()
    public ResponseEntity<byte[]> getAllAvatarsByPage(@RequestParam("page") Integer pageNumber,
                                                      @RequestParam("size") Integer pageSize) {
        List<Avatar> avatars = avatarService.getAvatars(pageNumber, pageSize);
        for (int i = 0; i < avatars.size(); i++) {
            Avatar tmp = avatars.get(i);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(tmp.getMediaType()));
            headers.setContentLength(tmp.getData().length);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(tmp.getData());
        }
        return null;
    }


}
