package ru.rs24.restapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }
}
