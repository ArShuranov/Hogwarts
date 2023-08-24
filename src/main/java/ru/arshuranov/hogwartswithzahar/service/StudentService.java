package ru.arshuranov.hogwartswithzahar.service;

import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    void getAllStudentsConsoleWithThreadsAsynch();

    void getAllStudentsConsoleWithThreadsSynch();
    List<String> nameStartWithLetter(String s);

    Student add(Student student);
    Student get(Long id);
    Student update(Long id, Student student);
    void remove(Long id);
    List<Student> getStudentsByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    Faculty getFacultyByStudent(Long id);

    int countStudents();

    Double avgAgeOfStudents();

    List<Student> getLastFiveStudents();

}
