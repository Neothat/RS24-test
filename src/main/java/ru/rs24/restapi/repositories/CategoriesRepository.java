package ru.rs24.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rs24.restapi.entities.Category;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
