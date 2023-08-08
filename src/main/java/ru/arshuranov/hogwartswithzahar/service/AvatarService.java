package ru.arshuranov.hogwartswithzahar.service;

import org.springframework.web.multipart.MultipartFile;
import ru.arshuranov.hogwartswithzahar.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);

    List<Avatar> getAvatars(Integer pageNumber, Integer pageSize);
}
