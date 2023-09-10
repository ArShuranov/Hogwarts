package ru.arshuranov.hogwartswithzahar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.repository.FacultyRepository;
import ru.arshuranov.hogwartswithzahar.service.FacultyService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    //inject repository
    private final FacultyRepository facultyRepository;

    //initialization logger
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //add faculty in to DB
    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    //get faculty by color
    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        logger.debug("Получаем факультет по цвету {}", color);
        List<Faculty> byColor = facultyRepository.findByColor(color);
        logger.debug("Получили следующий факультет по цвету {}, факультет {}", color, byColor);
        return byColor;
    }

    //get faculty by color or name
    @Override
    public List<Faculty> getFacultiesByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    //get all students of the faculty using the stream
    @Override
    public List<Student> getStudents(Long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElse(null);
    }

    //get the longest name of faculty using the stream
    @Override
    public Optional<String> mostLongNameOfFaculty() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));

    }

    //get faculty
    @Override
    public Faculty get(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    //update faculty
    @Override
    public Faculty update(Long id, Faculty faculty) {
        Faculty facultyFromDb = get(id);
        if (facultyFromDb == null) {
            return null;
        }
        facultyFromDb.setName(faculty.getName());
        facultyFromDb.setColor(faculty.getColor());
        return facultyRepository.save(facultyFromDb);
    }

    //remove faculty by id
    @Override
    public void remove(Long id) {
        facultyRepository.deleteById(id);
    }
}
