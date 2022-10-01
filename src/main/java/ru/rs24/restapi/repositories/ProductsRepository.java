package ru.rs24.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rs24.restapi.entities.Product;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
