package com.example.carpartsservice;


import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.model.Part;
import com.example.carpartsservice.repository.PartRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PartControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartRepository partRepository;

     private  Part part1 = new Part("RIDEX Remschijf", "Zonder bevestigingsbout, zonder wielnaaf", "1245745879654732", 12.95, new Category("Remsysteem"));
     private  Part part2 = new Part("RIDEX Remblokkenset", "Schijfrem", "147565748965247", 15.00, new Category("Remsysteem"));
     private  Part part3 = new Part("BLUE PRINT RE  MBLOKKENSET", "Remblokkenset", "394365748965784", 17.50, new Category("Remsysteem"));


     @BeforeEach
    public void beforeAllTests() {
         partRepository.deleteAll();
         partRepository.save(part1);
         partRepository.save(part2);
         partRepository.save(part3);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        partRepository.deleteAll();
    }

    //get all parts test
    @Test
    public void givenParts_whenGetParts_thenReturnJsonParts() throws Exception {

         Category category = new Category("Remsysteem");
        mockMvc.perform(get("/parts/view"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name",is("RIDEX Remschijf")))
                .andExpect(jsonPath("$[0].description",is("Zonder bevestigingsbout, zonder wielnaaf")))
                .andExpect(jsonPath("$[0].eanNumber",is("1245745879654732")))
                .andExpect(jsonPath("$[0].price",is(12.95)))
                .andExpect(jsonPath("$[0].category",is(category)))
                .andExpect(jsonPath("$[1].name",is("RIDEX Remblokkenset")))
                .andExpect(jsonPath("$[1].description",is("Schijfrem")))
                .andExpect(jsonPath("$[1].eanNumber",is("147565748965247")))
                .andExpect(jsonPath("$[1].price",is(15.00)))
                .andExpect(jsonPath("$[1].category",is(category)))
                .andExpect(jsonPath("$[2].name",is("BLUE PRINT RE  MBLOKKENSET")))
                .andExpect(jsonPath("$[2].description",is("Remblokkenset")))
                .andExpect(jsonPath("$[2].eanNumber",is("394365748965784")))
                .andExpect(jsonPath("$[2].price",is(17.50)))
                .andExpect(jsonPath("$[2].category",is(category)));
    }



}
