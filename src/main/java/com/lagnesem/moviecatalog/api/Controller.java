package com.lagnesem.moviecatalog.api;

import com.lagnesem.moviecatalog.dto.Director;
import com.lagnesem.moviecatalog.dto.Movie;
import com.lagnesem.moviecatalog.dto.Rating;
import com.lagnesem.moviecatalog.service.MovieService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final MovieService movieService;

    @Autowired
    public Controller(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/do-something")
    public List<Movie> doSomething() {
        IntStream.rangeClosed(1, 10)
                .forEach(x -> {
                    Director director = new Director();
                    director.setName(RandomStringUtils.random(10, true, false));
                    Movie movie = new Movie();
                    List<Rating> ratings = new ArrayList();
                    for (int i = 0; i < 4; i++) {
                        Rating rating = new Rating();
                        rating.setScore(ThreadLocalRandom.current().nextInt(0, 101));
                        rating.setComment("This is a test comment");
                        ratings.add(rating);
                    }
                    movie.setRatings(ratings);
                    movie.setTitle(String.format("Movie %s", RandomStringUtils.random(10, true, false)));
                    movie.setDirector(director);
                    movieService.saveMovie(movie);
                });
        return movieService.getAllMovies();
    }

}
