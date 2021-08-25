package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.CategoryDto;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.CategoryDao;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> findAll(){
        return categoryDao.findAll();
    }

    public Category findById(Long id){
        return categoryDao.findById(id).get();
    }

    public void deleteById(Long id){
        categoryDao.deleteById(id);
    }

    public void save(CategoryDto categoryDto){
        Category category = new Category(
                categoryDto.getId(),
                categoryDto.getName()
        );
        categoryDao.save(category);
    }

}
