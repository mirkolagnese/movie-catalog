package com.lagnesem.moviecatalog.api;

import com.lagnesem.moviecatalog.api.dto.Movie;
import com.lagnesem.moviecatalog.infra.entity.DirectorEntity;
import com.lagnesem.moviecatalog.infra.entity.MovieEntity;
import com.lagnesem.moviecatalog.infra.entity.RatingEntity;
import com.lagnesem.moviecatalog.service.MovieCatalogService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import javax.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * These methods are for testing purposes only.
 */
@RestController
public class MockDataController {

    private final MovieCatalogService movieCatalogService;

    @Autowired
    public MockDataController(MovieCatalogService movieCatalogService) {
        this.movieCatalogService = movieCatalogService;
    }

    /**
     * Creates an initial data set with random data
     *
     * @return
     */
    @Transactional
    @GetMapping("/init")
    public List<Movie> init() {
        IntStream.rangeClosed(1, 10)
                .forEach(x -> {
                    DirectorEntity director = new DirectorEntity();
                    director.setName(RandomStringUtils.random(10, true, false));
                    MovieEntity movie = new MovieEntity();
                    Set<RatingEntity> ratings = new HashSet<RatingEntity>();
                    for (int i = 0; i < 4; i++) {
                        RatingEntity rating = new RatingEntity();
                        rating.setScore(ThreadLocalRandom.current().nextInt(0, 101));
                        rating.setComment("This is a test comment");
                        ratings.add(rating);
                    }
                    movie.setRatings(ratings);
                    movie.setTitle(String.format("Movie %s", RandomStringUtils.random(10, true, false)));
                    Set<DirectorEntity> directors = new HashSet<>();
                    directors.add(director);
                    movie.setDirectors(directors);
                    movieCatalogService.saveMovie(movie);
                });

        return movieCatalogService.getAllMovies();
    }

    /**
     * deletes all entries from the database
     */
    @GetMapping("/reset")
    public void reset() {
        movieCatalogService.reset();
    }

}
