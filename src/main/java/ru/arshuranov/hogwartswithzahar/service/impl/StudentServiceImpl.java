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

    //initialization logger
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    //inject repository
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //get all students from DB
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    //study threads synch just for test
    @Override
    public void getAllStudentsConsoleWithThreadsSynch() {

        getNameFromStudentsForThreadsSynch(0);
        getNameFromStudentsForThreadsSynch(1);

        new Thread(() -> {
            getNameFromStudentsForThreadsSynch(2);
            getNameFromStudentsForThreadsSynch(3);

        }).start();

        new Thread(() -> {
            getNameFromStudentsForThreadsSynch(4);
            getNameFromStudentsForThreadsSynch(5);

        }).start();

    }

    //study threads acynch just for test
    @Override
    public void getAllStudentsConsoleWithThreadsAsynch() {

        getNameFromStudentsForThreadsAsynch(0);
        getNameFromStudentsForThreadsAsynch(1);

        new Thread(() -> {
            getNameFromStudentsForThreadsAsynch(2);
            getNameFromStudentsForThreadsAsynch(3);

        }).start();

        new Thread(() -> {
            getNameFromStudentsForThreadsAsynch(4);
            getNameFromStudentsForThreadsAsynch(5);

        }).start();
    }

    //get student by id without sunch for test thread asynch
    private void getNameFromStudentsForThreadsAsynch(int index) {
        String name;
        List<Student> tmp = getAllStudents();
        name = tmp.get(index).getName();
        System.out.println(name);
    }

    //get student with synch for test thread synch
    private synchronized void getNameFromStudentsForThreadsSynch(int index) {
        String name;
        List<Student> tmp = getAllStudents();
        name = tmp.get(index).getName();
        System.out.println(name);
    }

    //find students starting with letter (s) and return in upper case
    @Override
    public List<String> nameStartWithLetter(String s) {
        return getAllStudents().stream()
                .filter(student -> student.getName().toLowerCase().startsWith(s.toLowerCase()))
                .sorted(Comparator.comparing(Student::getName))
                .map(Student::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

    }

    //add student in to database
    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }


    //finding student by id and testing logger
    @Override
    public Student get(Long id) {
        logger.debug("Получаем студента по id {}", id);

        Student student = studentRepository.findById(id)
                .orElse(null);
        logger.debug("Вот такой получился студент {}", student);
        return student;
    }

    //update student
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

    //remove student
    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }

    //get students by age
    @Override
    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    //get students by age between
    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    //get student's faculty
    @Override
    public Faculty getFacultyByStudent(Long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty).orElse(null);
    }

    //counting all students
    @Override
    public int countStudents() {
        return studentRepository.countStudents();
    }

    //it for previous homework, returned avg age from DB
    //Этот метод возвращал средний возраст из базы данных, в новом задании следующий метод использую стримы и findAll
    /*@Override
    public float avgAgeOfStudents() {
        return studentRepository.avgAgeOfStudents();
    }*/

    //return average age using stream
    @Override
    public Double avgAgeOfStudents() {
        return getAllStudents().stream()
                .mapToDouble(Student::getAge).average()
                .getAsDouble();
    }

    //return last 5 students
    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }


}
