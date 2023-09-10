package ru.arshuranov.hogwartswithzahar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.arshuranov.hogwartswithzahar.dto.AvatarDTO;
import ru.arshuranov.hogwartswithzahar.mapper.AvatarMapper;
import ru.arshuranov.hogwartswithzahar.model.Avatar;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.repository.AvatarRepository;
import ru.arshuranov.hogwartswithzahar.repository.StudentRepository;
import ru.arshuranov.hogwartswithzahar.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarMapper avatarMapper;

    //initialization logger
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    //set directory for avatars
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository, AvatarMapper avatarMapper) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        this.avatarMapper = avatarMapper;
    }

    //upload avatar
    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {

        Student student = studentRepository.findById(studentId).orElseThrow();

        Path path = saveToDisk(student, avatarFile);
        saveToDb(student, avatarFile, path);


    }

    //save avatar in to DB
    private void saveToDb(Student student, MultipartFile avatarFile, Path filePath) throws IOException {

        Avatar avatar = findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    //save avatar to disk
    private Path saveToDisk(Student student, MultipartFile avatarFile) throws IOException {
        Path filePath = Path.of(
                avatarsDir,
                student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    public Avatar findAvatar(Long studentId) {
        return avatarRepository
                .findByStudent_Id(studentId)
                .orElse(new Avatar());

    }

    //output paginated avatars
    @Override
    public List<AvatarDTO> getPaginatedAvatars(int pageNumber, int pageSize) {
        logger.debug("Получаем список аватаров постранично: на {} странице, {} количество аватаров ", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<AvatarDTO> collect = avatarRepository
                .findAll(pageable)
                .getContent()
                .stream()
                .map(avatarMapper::mapToDTO)
                .collect(Collectors.toList());

        logger.debug("Получаем список аватаров постранично: {}", collect);
        return collect;
    }
    //doesn't matter it just for test parallel stream
    @Override
    public Integer intValue() {
        int sum = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        return sum;
    }

    //get extensions file of avatar for rename before save
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
