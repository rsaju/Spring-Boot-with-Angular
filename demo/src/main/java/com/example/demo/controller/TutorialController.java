package com.example.demo.controller;

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private TutorialRepository tutorialRepository;

    @GetMapping("tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutoriala(@RequestParam(required = false) String title){
        try{
            List<Tutorial> tutorials = new ArrayList<>();
            if(title == null){
                tutorialRepository.findAll().forEach(tutorials::add);
            }
            else {
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            }
            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return  new ResponseEntity<>(tutorials,HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
        Tutorial tutorialData = tutorialRepository.findById(id).orElse(null);
        if(tutorialData != null){
            return new ResponseEntity<>(tutorialData,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try{
            Tutorial tutorial1 = tutorialRepository.save(new Tutorial(tutorial.getTitle(),tutorial.getDescription(),false));
            return new ResponseEntity<>(tutorial1,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
        Tutorial tutorialData = tutorialRepository.findById(id).orElse(null);
        if(tutorialData != null){
            Tutorial tutorial1 = tutorialData;
            tutorial1.setTitle(tutorial.getTitle());
            tutorial1.setDescription(tutorial.getDescription());
            tutorial1.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(tutorial1), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try{
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
