package com.example.carpartsservice.controller;

import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.model.Part;
import com.example.carpartsservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void fillDB()
    {
        if(categoryRepository.count() == 0)
        {
            categoryRepository.save(new Category("Remschijven", "cat01"));
            categoryRepository.save(new Category("Autobatterijen", "cat02"));
            categoryRepository.save(new Category("Autobanden", "cat03"));
        }
    }

    //get all categories
    @GetMapping("/categories")
    public List<Category> getAllCategories()
    {
        return  categoryRepository.findAll();
    }

    // get category by ID
    @GetMapping("/categories/category/{categoryId}")
    public Category findCategoryById(@PathVariable String categoryId)
    {
        return  categoryRepository.findByCategoryId(categoryId);
    }

    // get category bij name

    //Add a category
    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category)
    {
        categoryRepository.save(category);
        return category;
    }

    //update
    @PutMapping("/categories")
    public Category updateCategory(@RequestBody Category category)
    {
        Category toUpdateCategory = categoryRepository.findByCategoryId(category.getCategoryId());
        // update
        toUpdateCategory.setName(category.getName());

        categoryRepository.save(toUpdateCategory);
        return toUpdateCategory;

    }

    //delete category
    @DeleteMapping("/categories/category/{categoryID}")
    public ResponseEntity deletePart(@PathVariable String categoryID)
    {
        Category toDeleteCategory = categoryRepository.findByCategoryId(categoryID);
        if(toDeleteCategory != null){
            categoryRepository.delete(toDeleteCategory);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }




}
