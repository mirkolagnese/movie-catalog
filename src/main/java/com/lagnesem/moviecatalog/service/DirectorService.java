package com.lagnesem.moviecatalog.service;

import com.lagnesem.moviecatalog.infra.DirectorRepository;
import com.lagnesem.moviecatalog.infra.entity.DirectorEntity;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectorService.class);

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<DirectorEntity> getAllDirectors() {
        List<DirectorEntity> result = directorRepository.findAll();
        LOGGER.info("Retrieved directors {}", result);
        return result;
    }

    public DirectorEntity getDirectorById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    public DirectorEntity saveDirector(DirectorEntity director) {
        LOGGER.info("Saving director {}", director);
        return directorRepository.saveAndFlush(director);
    }

    public DirectorEntity updateDirector(DirectorEntity director) {
        Optional<DirectorEntity> fromDb = directorRepository.findById(director.getId());
        if (fromDb.isPresent()) {
            DirectorEntity storedObj = fromDb.get();
            storedObj.setName(director.getName());
            return directorRepository.saveAndFlush(storedObj);
        }
        return null;
    }
}
