package ru.arshuranov.hogwartswithzahar.service.impl;

import org.springframework.stereotype.Service;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long counter = 0;

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(++counter);
        faculties.put(faculty.getId(), faculty);
        return faculties.get(faculty.getId());
    }

    @Override
    public Map<Long, Faculty> getAll() {
        return faculties;
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
       return faculties.values()
                .stream()
                .filter(it -> it.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Faculty get(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        faculty.setId(id);
        return faculties.put(faculty.getId(), faculty);
    }

    @Override
    public void remove(Long id) {
        faculties.remove(id);
    }
}
