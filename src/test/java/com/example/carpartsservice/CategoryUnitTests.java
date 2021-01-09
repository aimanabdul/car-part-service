package com.example.carpartsservice;


import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.model.Part;
import com.example.carpartsservice.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CategoryRepository categoryRepository;

    private ObjectMapper mapper = new ObjectMapper();

    //get all categories
    //get all parts test
    @Test
    public void givenCategories_whenGetAllCategories_thenReturnJsonCategories() throws Exception
    {
        Category category1 = new Category("Remsysteem", "cat01");
        Category category2 = new Category("Motor", "cat02");
        Category category3 = new Category("Vering / Demping","cat03");

        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(category1);
        categoriesList.add(category2);
        categoriesList.add(category3);

        given(categoryRepository.findAll()).willReturn(categoriesList);

        mockMvc.perform(get("/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name",is("Remsysteem")))
                .andExpect(jsonPath("$[1].name",is("Motor")))
                .andExpect(jsonPath("$[2].name",is("Vering / Demping")));

    }

    //find category by ID
    @Test
    public void givenCategory_whenFindCategoryById_thenReturnJsonCategory() throws Exception
    {
        Category category1 = new Category("Remsysteem", "cat01");

        given(categoryRepository.findByCategoryId("cat01")).willReturn(category1);

        mockMvc.perform(get("/categories/category/{categoryId}", "cat01"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Remsysteem")));

    }


    // add a category
    @Test
    public void givenCategory_whenPostCategory_thenReturnJsonCategory() throws Exception
    {
        Category category1 = new Category("Remsysteem", "cat01");

        mockMvc.perform(post("/categories")
                .content(mapper.writeValueAsString(category1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Remsysteem")));

    }

    //update category
//    @Test
//    public void givenCategory_whenPutCategory_thenReturnJsonCategory() throws Exception
//    {
//        Category toUpdateCategory = new Category("Remsysteem");
//
//        given(categoryRepository.findCategoryById(1)).willReturn(toUpdateCategory);
//
//        Category newCategory = new Category("Carrosserie");
//
//        mockMvc.perform(put("/categories")
//                .content(mapper.writeValueAsString(newCategory))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name",is("Carrosserie")));
//
//    }

    //delete
    @Test
    public void givenCategory_whenDeleteCategory_thenStatusOk() throws Exception {

        Category category = new Category("Carrosserie", "cat02");

        given(categoryRepository.findByCategoryId("cat02")).willReturn(category);
        mockMvc.perform(delete("/categories/category/{categoryID}", "cat02")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoCategory_whenDeleteCategory_thenStatusNotFound() throws Exception {

        given(categoryRepository.findByCategoryId("cat012134")).willReturn(null);
        mockMvc.perform(delete("/categories/category/{categoryID}", "cat012134")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
