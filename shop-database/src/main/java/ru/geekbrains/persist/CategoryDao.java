package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.model.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
