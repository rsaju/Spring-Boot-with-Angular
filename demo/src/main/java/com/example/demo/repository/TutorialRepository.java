package com.example.demo.repository;

import com.example.demo.model.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {

    List<Tutorial> findByTitleContaining(String title);
    List<Tutorial> findByPublished(boolean published);
}
