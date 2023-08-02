package ru.arshuranov.hogwartswithzahar.service.impl;

import org.springframework.stereotype.Service;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.repository.StudentRepository;
import ru.arshuranov.hogwartswithzahar.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /*@Override
    public Map<Long, Student> getAll() {
        return null;
    }*/

    @Override
    public Student add(Student student) {
       return studentRepository.save(student);
    }


    @Override
    public Student get(Long id) {
        return studentRepository.findById(id)
                .orElse(null);
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


}
