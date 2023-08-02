package ru.arshuranov.hogwartswithzahar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arshuranov.hogwartswithzahar.model.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);
    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);
}
