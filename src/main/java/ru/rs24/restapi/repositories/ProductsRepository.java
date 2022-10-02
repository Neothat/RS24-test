package ru.rs24.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.rs24.restapi.entities.Category;
import ru.rs24.restapi.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);

    List<Product> findByCategory(Category category);
}
