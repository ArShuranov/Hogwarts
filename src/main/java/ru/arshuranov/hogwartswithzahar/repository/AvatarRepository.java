package ru.arshuranov.hogwartswithzahar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arshuranov.hogwartswithzahar.model.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudent_Id(Long studentId);

    //@Query(value = "SELECT * from avatar", nativeQuery = true)
    List<Avatar> findAll();
}
