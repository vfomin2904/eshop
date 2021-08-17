package ru.geekbrains.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    private Integer price;


    private CategoryDto category;

    public ProductDto() {
    }

    public ProductDto(Long id, @NotBlank String name, Integer price, CategoryDto category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public ProductDto(Long id, @NotBlank String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
