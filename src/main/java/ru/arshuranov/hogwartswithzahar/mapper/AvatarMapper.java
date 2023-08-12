package ru.arshuranov.hogwartswithzahar.mapper;

import org.springframework.stereotype.Component;
import ru.arshuranov.hogwartswithzahar.dto.AvatarDTO;
import ru.arshuranov.hogwartswithzahar.model.Avatar;

@Component
public class AvatarMapper {
    public AvatarDTO mapToDTO(Avatar avatar) {
        return new AvatarDTO(
                avatar.getId(),
                avatar.getFilePath(),
                avatar.getFileSize(),
                avatar.getMediaType(),
                avatar.getStudent().getId()
        );
    }
}
