package com.example.carpartsservice;

import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.model.Part;
import com.example.carpartsservice.repository.PartRepository;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PartControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartRepository partRepository;

    //Mapper
    private ObjectMapper mapper = new ObjectMapper();

    //get all parts test

    @Test
    public void givenParts_whenGetParts_thenReturnJsonParts() throws Exception {
         Part part1 = new Part("RIDEX Remschijf", "Remschijf zonder bevestigingsbout, zonder wielnaaf", "1245745879654732", 12.95, new Category("Remsysteem"));
         Part part2 = new Part("RIDEX Remblokkenset", "Remschijf", "147565748965247", 15.00, new Category("Remsysteem"));
         Part part3 = new Part("BLUE PRINT RE  MBLOKKENSET", "Remschijf, Remblokkenset", "394365748965784", 17.50, new Category("Remsysteem"));

        List<Part> partslist = new ArrayList<>();
        partslist.add(part1);
        partslist.add(part2);
        partslist.add(part3);

        given(partRepository.findAll()).willReturn(partslist);


        Category category = new Category("Remsysteem");
        mockMvc.perform(get("/parts/view"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name",is("RIDEX Remschijf")))
                .andExpect(jsonPath("$[0].description",is("Remschijf zonder bevestigingsbout, zonder wielnaaf")))
                .andExpect(jsonPath("$[0].eanNumber",is("1245745879654732")))
                .andExpect(jsonPath("$[0].price",is(12.95)))
                .andExpect(jsonPath("$[0].category.name",is("Remsysteem")))
                .andExpect(jsonPath("$[1].name",is("RIDEX Remblokkenset")))
                .andExpect(jsonPath("$[1].description",is("Remschijf")))
                .andExpect(jsonPath("$[1].eanNumber",is("147565748965247")))
                .andExpect(jsonPath("$[1].price",is(15.00)))
                .andExpect(jsonPath("$[1].category.name",is("Remsysteem")))
                .andExpect(jsonPath("$[2].name",is("BLUE PRINT RE  MBLOKKENSET")))
                .andExpect(jsonPath("$[2].description",is("Remschijf, Remblokkenset")))
                .andExpect(jsonPath("$[2].eanNumber",is("394365748965784")))
                .andExpect(jsonPath("$[2].price",is(17.50)))
                .andExpect(jsonPath("$[2].category.name",is("Remsysteem")));
    }


    //get part by eanNumber
    @Test
    public void givenPart_whenGetPartByEanNumber_thenReturnJsonPart() throws Exception
    {
        Part part1 = new Part("RIDEX Remschijf", "Remschijf zonder bevestigingsbout, zonder wielnaaf", "1245745879654732", 12.95, new Category("Remsysteem"));
        //Part part2 = new Part("RIDEX Remblokkenset", "Remschijf", "147565748965247", 15.00, new Category("Remsysteem"));
        //Part part3 = new Part("BLUE PRINT RE  MBLOKKENSET", "Remschijf, Remblokkenset", "394365748965784", 17.50, new Category("Remsysteem"));

        given(partRepository.findPartByEanNumber("1245745879654732")).willReturn(part1);

        Category category = new Category("Remsysteem");
        mockMvc.perform(get("/parts/part/{eanNumber}", "1245745879654732"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("RIDEX Remschijf")))
                .andExpect(jsonPath("$.description",is("Remschijf zonder bevestigingsbout, zonder wielnaaf")))
                .andExpect(jsonPath("$.eanNumber",is("1245745879654732")))
                .andExpect(jsonPath("$.price",is(12.95)))
                .andExpect(jsonPath("$.category.getName",is("Remsysteem")));
    }


    //get part by description
    @Test
    public void givenPart_whenFindPartsByDescriptionContaining_thenReturnJsonPart() throws Exception
    {
        Part part1 = new Part("RIDEX Remschijf", "Remschijf zonder bevestigingsbout, zonder wielnaaf", "1245745879654732", 12.95, new Category("Remsysteem"));
        Part part2 = new Part("RIDEX Remblokkenset", "Remschijf", "147565748965247", 15.00, new Category("Remsysteem"));
        Part part3 = new Part("BLUE PRINT RE  MBLOKKENSET", "Remschijf, Remblokkenset", "394365748965784", 17.50, new Category("Remsysteem"));

        partRepository.save(part1);
        partRepository.save(part2);
        partRepository.save(part3);

        List<Part> partslist = new ArrayList<>();
        partslist.add(part1);
        partslist.add(part2);
        partslist.add(part3);

        given(partRepository.findPartsByDescriptionContaining("Remschijf")).willReturn(partslist);


        mockMvc.perform(get("/parts/part/{description}", "Remschijf"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name",is("RIDEX Remschijf")))
                .andExpect(jsonPath("$[0].description",is("Remschijf zonder bevestigingsbout, zonder wielnaaf")))
                .andExpect(jsonPath("$[0].eanNumber",is("1245745879654732")))
                .andExpect(jsonPath("$[0].price",is(12.95)))
                .andExpect(jsonPath("$[0].category.name",is("Remsysteem")))
                .andExpect(jsonPath("$[1].name",is("RIDEX Remblokkenset")))
                .andExpect(jsonPath("$[1].description",is("Remschijf")))
                .andExpect(jsonPath("$[1].eanNumber",is("147565748965247")))
                .andExpect(jsonPath("$[1].price",is(15.00)))
                .andExpect(jsonPath("$[1].category.name",is("Remsysteem")))
                .andExpect(jsonPath("$[2].name",is("BLUE PRINT RE  MBLOKKENSET")))
                .andExpect(jsonPath("$[2].description",is("Remschijf, Remblokkenset")))
                .andExpect(jsonPath("$[2].eanNumber",is("394365748965784")))
                .andExpect(jsonPath("$[2].price",is(17.50)))
                .andExpect(jsonPath("$[2].category.name",is("Remsysteem")));
    }


    //Add a part
    @Test
    public void whenPostPart_thenReturnJsonPart() throws Exception {
        Part part = new Part("POSCH Getande riem", "Distributieriem", "218865755965790", 17.50, new Category("Motor"));

        mockMvc.perform(post("/parts")
                .content(mapper.writeValueAsString(part))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("POSCH Getande riem")))
                .andExpect(jsonPath("$.description",is("Distributieriem")))
                .andExpect(jsonPath("$.eanNumber",is("218865755965790")))
                .andExpect(jsonPath("$.price",is(17.50)))
                .andExpect(jsonPath("$.category.name",is("Motor")));
    }


    //Update
    @Test
    public void givenPart_whenPutPart_thenReturnJsonPart() throws Exception {

        Part toUpdatepart = new Part("MASTER-SPORT Remschijf", "Remschijf, SPORT-REMSCHIJF", "1245745879654732", 30.85, new Category("Remsysteem"));

        partRepository.save(toUpdatepart);

        given(partRepository.findPartByEanNumber("1245745879654732")).willReturn(toUpdatepart);

        Part newPart = new Part("Regular Remschijf", "Remschijf, Regular", "1245745879654732", 12.50, new Category("Remsysteem"));

        
        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(newPart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Regular Remschijf")))
                .andExpect(jsonPath("$.description",is("Remschijf, Regular")))
                .andExpect(jsonPath("$.eanNumber",is("1245745879654732")))
                .andExpect(jsonPath("$.price",is(12.50)))
                .andExpect(jsonPath("$.category.name",is("Remsysteem")));
    }

    //delete
    @Test
    public void givenPart_whenDeletePart_thenStatusOk() throws Exception {

        Part part = new Part("MASTER-SPORT Remschijf", "Remschijf, SPORT-REMSCHIJF", "1245745879654732", 30.85, new Category("Remsysteem"));

        partRepository.save(part);
        given(partRepository.findPartByEanNumber("1245745879654732")).willReturn(part);
        mockMvc.perform(delete("parts/part/{eanNumber}", "1245745879654732")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoPart_whenDeletePart_thenStatusNotFound() throws Exception {
        Part part = new Part("MASTER-SPORT Remschijf", "Remschijf, SPORT-REMSCHIJF", "1245745879654732", 30.85, new Category("Remsysteem"));

        partRepository.save(part);
        given(partRepository.findPartByEanNumber("1245745879654700")).willReturn(null);
        mockMvc.perform(delete("parts/part/{eanNumber}", "1245745879654700")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
