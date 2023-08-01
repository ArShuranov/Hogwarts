package ru.arshuranov.hogwartswithzahar.service;

import ru.arshuranov.hogwartswithzahar.model.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Map<Long, Student> getAll();

    Student add(Student student);
    Student get(Long id);
    Student update(Long id, Student student);
    void remove(Long id);
    List<Student> getStudentsByAge(int age);

}