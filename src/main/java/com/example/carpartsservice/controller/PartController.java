package com.example.carpartsservice.controller;

import com.example.carpartsservice.model.Category;
import com.example.carpartsservice.model.Part;
import com.example.carpartsservice.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class PartController {
    @Autowired
    private PartRepository partRepository;

    @PostConstruct
    public  void fillDB()
    {
        if(partRepository.count() == 0)
        {
            partRepository.save(new Part("RIDEX Remschijf", "Zonder bevestigingsbout, zonder wielnaaf", "1245745879654732", 12.95, new Category("Remsysteem")));
            partRepository.save(new Part("RIDEX Remblokkenset", "Schijfrem", "147565748965247", 15.00, new Category("Remsysteem")));
            partRepository.save(new Part("RIDEX Remblokkenset", "Schijfrem", "147565748965247", 17.50, new Category("Remsysteem")));
        }
        System.out.println(partRepository.findPartByEanNumber("147565748965247").getName());
    }


    //welcome
    @GetMapping("/parts")
    public String viewWelcome()
    {
        return  "app works!";

    }

    // get all parts
    @GetMapping("/parts/view")
    public List<Part> getParts()
    {
        return  partRepository.findAll();
    }

    //get part by eanNumber
    @GetMapping("/parts/part/{eanNumber}")
    public Part getPartById(@PathVariable String eanNumber)
    {
        return  partRepository.findPartByEanNumber(eanNumber);

    }

    //get part by description
    @GetMapping("/parts/{description}")
    public List<Part> getPartsByDescription(@PathVariable String description)
    {
        return  partRepository.findPartsByDescriptionContaining(description);

    }

    //Add a part
    @PostMapping("/parts")
    public Part addPart(@RequestBody Part part)
    {
        partRepository.save(part);
        return part;
    }

    @PutMapping("/parts")
    public Part updatePart(@RequestBody Part part)
    {
        Part toUpdatePart = partRepository.findPartByIdAndEanNumber(part.getId(), part.getEanNumber());
        // update
        toUpdatePart.setCategory(part.getCategory());
        toUpdatePart.setDescription(part.getDescription());
        toUpdatePart.setName(part.getName());
        toUpdatePart.setPrice(part.getPrice());

        partRepository.save(toUpdatePart);
        return toUpdatePart;

    }

    @DeleteMapping("parts/part/{eanNumber}")
    public ResponseEntity deletePart(@PathVariable String eanNumber)
    {
        Part toDeletePart = partRepository.findPartByEanNumber(eanNumber);
        if(toDeletePart != null){
            partRepository.delete(toDeletePart);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }



}
