package ru.arshuranov.hogwartswithzahar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arshuranov.hogwartswithzahar.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudent_Id(Long studentId);
}
