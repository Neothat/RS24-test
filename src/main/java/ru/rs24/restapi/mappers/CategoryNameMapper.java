package ru.rs24.restapi.mappers;

import org.springframework.stereotype.Component;
import ru.rs24.restapi.entities.Category;

@Component
public class CategoryNameMapper {

    public String toString(Category category) {
        return category != null
                ? category.getName()
                : null;
    }
}
