package com.tsayvyac.task.service;

import com.tsayvyac.task.dto.technology.TechnologyDetailsResponse;
import com.tsayvyac.task.dto.technology.TechnologyRequest;
import com.tsayvyac.task.dto.technology.TechnologyResponse;
import com.tsayvyac.task.exception.TechnologyException;
import com.tsayvyac.task.model.Technology;
import com.tsayvyac.task.repository.TechnologyRepository;
import com.tsayvyac.task.repository.pojo.TechnologyCount;
import com.tsayvyac.task.service.mapper.TechnologyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tsayvyac.task.util.Constant.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
class TechnologyService implements ITechnologyService {
    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;

    @Override
    public void deleteTechnology(Long id) {
        Optional<Technology> technologyToDelete = technologyRepository.findById(id);
        if (technologyToDelete.isPresent()) {
            technologyRepository.delete(technologyToDelete.get());
        } else throw new TechnologyException(T_WITH_ID + id + NOT_FOUND);
    }

    @Override
    public void updateTechnology(Long id, TechnologyRequest technologyRequest) {
        technologyRepository.save(technologyRepository.findById(id)
                .map(technology -> {
                    technology.setName(technologyRequest.getName());
                    return technology;
                })
                .orElseThrow(() -> new TechnologyException(T_WITH_ID + id + NOT_FOUND))
        );
        log.info("{}{} was saved!", T_WITH_NAME, technologyRequest.getName());
    }

    @Override
    public TechnologyDetailsResponse getTechnologyDetails(Long id) {
        return technologyRepository.findById(id)
                .map(technologyMapper::mapToDetailsResponse)
                .orElseThrow(() -> new TechnologyException(T_WITH_ID + id + NOT_FOUND));
    }

    @Override
    public List<TechnologyResponse> getAllTechnologies() {
        return technologyRepository.findAll()
                .stream()
                .map(technologyMapper::mapToResponse)
                .toList();
    }

    @Override
    public void addTechnology(TechnologyRequest technologyRequest) {
        if (technologyRepository.findByName(technologyRequest.getName()).isPresent())
            throw new TechnologyException(T_WITH_NAME + technologyRequest.getName() + " already exist in database");

        Technology technology = Technology.builder()
                .name(technologyRequest.getName())
                .build();
        technologyRepository.save(technology);
        log.info("{}{} is saved!", T_WITH_NAME, technology.getName());
    }

    @Override
    public List<TechnologyCount> getCountOfUsingTechnology() {
        return technologyRepository.getCountOfUsingTechnology();
    }

    Long getTechnologyId(String name) {
        return technologyRepository.findByName(name)
                .map(Technology::getId)
                .orElseThrow(() -> new TechnologyException(T_WITH_NAME + name + NOT_FOUND));
    }
}
