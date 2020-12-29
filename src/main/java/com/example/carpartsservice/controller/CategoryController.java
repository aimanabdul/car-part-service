package com.example.carpartsservice.controller;

import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.model.Part;
import com.example.carpartsservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    //get all categories
    @GetMapping("/categories")
    public List<Category> getAllCategories()
    {
        return  categoryRepository.findAll();
    }

    // get category by ID
    @GetMapping("/categories/category/{categoryID}")
    public Category findCategoryById(@PathVariable int categoryID)
    {
        return  categoryRepository.findCategoryById(categoryID);
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
        Category toUpdateCategory = categoryRepository.findCategoryById(category.getId());
        // update
        toUpdateCategory.setName(category.getName());

        categoryRepository.save(toUpdateCategory);
        return toUpdateCategory;

    }

    //delete category
    @DeleteMapping("/categories/category/{categoryID}")
    public ResponseEntity deletePart(@PathVariable int categoryID)
    {
        Category toDeleteCategory = categoryRepository.findCategoryById(categoryID);
        if(toDeleteCategory != null){
            categoryRepository.delete(toDeleteCategory);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }




}
