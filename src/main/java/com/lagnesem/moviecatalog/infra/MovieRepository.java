package com.lagnesem.moviecatalog.infra;

import com.lagnesem.moviecatalog.dto.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
