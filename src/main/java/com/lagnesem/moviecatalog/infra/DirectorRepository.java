package com.lagnesem.moviecatalog.infra;

import com.lagnesem.moviecatalog.dto.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {

}
