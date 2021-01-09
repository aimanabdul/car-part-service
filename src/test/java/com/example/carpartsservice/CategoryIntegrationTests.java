package com.example.carpartsservice;

import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;


    private ObjectMapper mapper = new ObjectMapper();

    Category category1 = new Category("Motor", "cat01");
    Category category2 = new Category("Remsysteem", "cat02");
    Category category3 = new Category("Elektrische systemen", "cat03");


    @BeforeEach
    public void beforeAllTests()
    {
        categoryRepository.deleteAll();
        category1.setId(1);
        category2.setId(2);
        category3.setId(3);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
    }

    @AfterEach
    public void afterAllTests()
    {
        //Watch out with deleteAll() methods when you have other data in the test database!
        categoryRepository.deleteAll();
    }

    // get all categories
    @Test
    public void givenCategories_whenGetCategories_thenReturnJsonCategories() throws Exception
    {
        mockMvc.perform(get("/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name",is("Motor")))
                .andExpect(jsonPath("$[1].name",is("Remsysteem")))
                .andExpect(jsonPath("$[2].name",is("Elektrische systemen")));
    }

    // category by Id
//    @Test
//    public void givenCategory_whenGetCategoryById_thenReturnJsonCategory() throws Exception
//    {
//        mockMvc.perform(get("/categories/category/{categoryID}", 1))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name",is("Motor")));
//    }


    // add category
    @Test
    public void givenCategory_whenPostCategory_thenReturnJsonCategory() throws Exception
    {
        Category toBeAddedCategory= new Category("Vering /Demping", "cat04");

        mockMvc.perform(post("/categories")
                .content(mapper.writeValueAsString(toBeAddedCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Vering /Demping")));
    }

    // update
//    @Test
//    public void givenCategory_whenPutCategory_thenReturnJsonCategory() throws Exception
//    {
//        Category toUpdateCategory = new Category("Banden");
//        toUpdateCategory.setId(1);
//
//        mockMvc.perform(put("/categories")
//                .content(mapper.writeValueAsString(toUpdateCategory))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", is("Banden")));
//
//    }


    //delete
//    @Test
//    public void givenCategory_whenDeleteCategory_thenStatusOk() throws Exception
//    {
//        mockMvc.perform(delete("/categories/category/{categoryID}", 1)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }

    @Test
    public void givenNoCategory_whenDeleteCategory_thenStatusOk() throws Exception
    {
        mockMvc.perform(delete("/categories/category/{categoryID}",  99)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }



}
