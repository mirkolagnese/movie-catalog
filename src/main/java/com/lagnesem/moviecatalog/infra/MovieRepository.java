package com.lagnesem.moviecatalog.infra;

import com.lagnesem.moviecatalog.infra.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

}
