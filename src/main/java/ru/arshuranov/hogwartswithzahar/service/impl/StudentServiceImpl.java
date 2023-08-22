package ru.arshuranov.hogwartswithzahar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.repository.StudentRepository;
import ru.arshuranov.hogwartswithzahar.service.StudentService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<String> nameStartWithLetter(String s) {
        return getAllStudents().stream()
                .filter(student -> student.getName().toLowerCase().startsWith(s.toLowerCase()))
                .sorted(Comparator.comparing(Student::getName))
                .map(Student::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }


    @Override
    public Student get(Long id) {
        logger.debug("Получаем студента по id {}", id);

        Student student = studentRepository.findById(id)
                .orElse(null);
        logger.debug("Вот такой получился студент {}", student);
        return student;
    }

    @Override
    public Student update(Long id, Student student) {
        Student studentFromDb = get(id);
        if (studentFromDb == null) {
            return null;
        }
        studentFromDb.setName(student.getName());
        studentFromDb.setAge(student.getAge());
        return studentRepository.save(studentFromDb);
    }

    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudent(Long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty).orElse(null);
    }

    @Override
    public int countStudents() {
        return studentRepository.countStudents();
    }

    //Этот метод возвращал средний возраст из базы данных, в новом задании следующий метод использую стримы и findAll
    /*@Override
    public float avgAgeOfStudents() {
        return studentRepository.avgAgeOfStudents();
    }*/

    @Override
    public Double avgAgeOfStudents() {
        return getAllStudents().stream()
                .mapToDouble(Student::getAge).average()
                .getAsDouble();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }


}
