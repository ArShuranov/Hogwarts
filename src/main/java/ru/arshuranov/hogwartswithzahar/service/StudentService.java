package ru.arshuranov.hogwartswithzahar.service;

import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;

import java.util.List;

public interface StudentService {

    Student add(Student student);
    Student get(Long id);
    Student update(Long id, Student student);
    void remove(Long id);
    List<Student> getStudentsByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Faculty getFacultyByStudent(Long id);

    int countStudents();

    float avgAgeOfStudents();

    List<Student> getLastFiveStudents();

}
