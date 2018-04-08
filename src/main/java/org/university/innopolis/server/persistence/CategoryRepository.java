package org.university.innopolis.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.university.innopolis.server.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Category getById(int id);
}
