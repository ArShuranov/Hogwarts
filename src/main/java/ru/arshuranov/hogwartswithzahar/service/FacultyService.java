package ru.arshuranov.hogwartswithzahar.service;

import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;

import java.util.List;
import java.util.Map;

public interface FacultyService {

    /*Map<Long, Faculty> getAll();*/
    Faculty add(Faculty faculty);
    Faculty get(Long id);
    Faculty update(Long id, Faculty faculty);
    void remove(Long id);
     List<Faculty> getFacultiesByColor(String color);

    List<Faculty> getFacultiesByColorOrName(String color, String name);

    List<Student> getStudents(Long id);

}
