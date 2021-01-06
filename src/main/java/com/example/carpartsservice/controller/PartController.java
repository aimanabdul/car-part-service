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
    public void fillDB()
    {
        if(partRepository.count() == 0)
        {
            partRepository.save(new Part("RIDEX Remschijf", "Zonder bevestigingsbout, zonder wielnaaf", "3572245731064 ", 12.95, 1));
            partRepository.save(new Part("RIDEX Remblokkenset", "Schijfrem", "8177540401215", 15.00, 1));
            partRepository.save(new Part("Varta Blue Dynamic 063 Battery", "25 x 24 x 18 cm; 11.29 Kilograms; 12 Volts", "6448177804482", 43.96, 2));
            partRepository.save(new Part("Hankook Ventus Prime 2", "Zomerwielen voor personenwagen", "4585783153449", 150, 3));
        }
    }



    // get all parts
    @GetMapping("/parts/view")
    public List<Part> getParts()
    {
        return  partRepository.findAll();
    }

    //get part by eanNumber
    @GetMapping("/parts/part/{eanNumber}")
    public Part getPartByEanNumber(@PathVariable String eanNumber)
    {
        return  partRepository.findPartByEanNumber(eanNumber);

    }

    //get part by categoryID
    @GetMapping("/parts/category/{categoryID}")
    public List<Part> findPartsByCategoryID(@PathVariable int categoryID)
    {
        return  partRepository.findAllByCategoryID(categoryID);

    }

    //Add a part
    @PostMapping("/parts")
    public Part addPart(@RequestBody Part part)
    {
        partRepository.save(part);
        return part;
    }


    //update
    @PutMapping("/parts")
    public Part updatePart(@RequestBody Part part)
    {
        Part toUpdatePart = partRepository.findPartByEanNumber(part.getEanNumber());
        // update
        toUpdatePart.setCategoryID(part.getCategoryID());
        toUpdatePart.setDescription(part.getDescription());
        toUpdatePart.setName(part.getName());
        toUpdatePart.setPrice(part.getPrice());
        toUpdatePart.setCategoryID(part.getCategoryID());

        partRepository.save(toUpdatePart);
        return toUpdatePart;

    }

    @DeleteMapping("/parts/part/{eanNumber}")
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
