package com.lagnesem.moviecatalog.infra;

import com.lagnesem.moviecatalog.infra.entity.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {

}
