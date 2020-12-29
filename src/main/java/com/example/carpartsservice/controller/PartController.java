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


// since we working with testing,we don't need the postConstructor
//    @PostConstruct
//    public  void fillDB()
//    {
//        if(partRepository.count() == 0)
//        {
//            partRepository.save(new Part("RIDEX Remschijf", "Zonder bevestigingsbout, zonder wielnaaf", "1245745879654732", 12.95, 1));
//            partRepository.save(new Part("RIDEX Remblokkenset", "Schijfrem", "147565748965247", 15.00, 1));
//            partRepository.save(new Part("RIDEX Remblokkenset", "Schijfrem", "147565748965247", 17.50, 1));
//        }
//        System.out.println(partRepository.findPartByEanNumber("147565748965247").getName());
//    }



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
