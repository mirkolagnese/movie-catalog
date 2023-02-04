package com.lagnesem.moviecatalog.service;

import com.lagnesem.moviecatalog.dto.Director;
import com.lagnesem.moviecatalog.infra.DirectorRepository;
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

    public List<Director> getAllDirectors() {
        List<Director> result = directorRepository.findAll();
        LOGGER.info("Retrieved directors {}", result);
        return result;
    }

    public Director getDirectorById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    public Director saveDirector(Director director) {
        LOGGER.info("Saving director {}", director);
        return directorRepository.saveAndFlush(director);
    }

    public Director updateDirector(Director director) {
        Optional<Director> fromDb = directorRepository.findById(director.getId());
        if (fromDb.isPresent()) {
            Director storedObj = fromDb.get();
            storedObj.setName(director.getName());
            return directorRepository.saveAndFlush(storedObj);
        }
        return null;
    }
}
