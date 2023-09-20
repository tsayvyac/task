package com.tsayvyac.task.controller;

import com.tsayvyac.task.dto.technology.TechnologyRequest;
import com.tsayvyac.task.dto.technology.TechnologyResponse;
import com.tsayvyac.task.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tech")
@RequiredArgsConstructor
public class TechnologyController {
    private final TechnologyService technologyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TechnologyResponse> getAllTechnologies() {
        return technologyService.getAllTechnologies();
    }

//    TODO: How to get details? What is it supposed to look like?
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Technology getTechnologyDetailsById(@PathVariable Long id) {
//
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTechnology(@RequestBody TechnologyRequest technology) {
        technologyService.addTechnology(technology);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateTechnology(@PathVariable Long id, @RequestBody TechnologyRequest technologyRequest) {
        technologyService.updateTechnology(id, technologyRequest);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
    }
}
