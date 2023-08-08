package ru.arshuranov.hogwartswithzahar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.arshuranov.hogwartswithzahar.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "select count(*) from student", nativeQuery = true)
    int countStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    float avgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();


}
