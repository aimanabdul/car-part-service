package com.example.carpartsservice.repository;

import com.example.carpartsservice.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {


    Part findPartByEanNumber (String eanNumber);

    List<Part> findAllByDescriptionLike(String description);

}
