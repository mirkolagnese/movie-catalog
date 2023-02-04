package com.lagnesem.moviecatalog.api;

import com.lagnesem.moviecatalog.dto.Director;
import com.lagnesem.moviecatalog.dto.Movie;
import com.lagnesem.moviecatalog.dto.Rating;
import com.lagnesem.moviecatalog.service.DirectorService;
import com.lagnesem.moviecatalog.service.MovieService;
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

@RestController
public class Controller {

    private final MovieService movieService;
    private final DirectorService directorService;

    @Autowired
    public Controller(MovieService movieService, DirectorService directorService) {
        this.movieService = movieService;
        this.directorService = directorService;
    }

    @Transactional
    @GetMapping("/do-something")
    public List<Movie> doSomething() {
        IntStream.rangeClosed(1, 10)
                .forEach(x -> {
                    Director director = new Director();
                    director.setName(RandomStringUtils.random(10, true, false));
                    Movie movie = new Movie();
                    Set<Rating> ratings = new HashSet<Rating>();
                    for (int i = 0; i < 4; i++) {
                        Rating rating = new Rating();
                        rating.setScore(ThreadLocalRandom.current().nextInt(0, 101));
                        rating.setComment("This is a test comment");
                        ratings.add(rating);
                    }
                    movie.setRatings(ratings);
                    movie.setTitle(String.format("Movie %s", RandomStringUtils.random(10, true, false)));
                    Set<Director> directors = new HashSet<>();
                    directors.add(director);
                    movie.setDirectors(directors);
                    movieService.saveMovie(movie);
                });

        List<Movie> allMovies = movieService.getAllMovies();
        Movie movie = allMovies.get(0);
        movieService.deleteMovie(movie.getId());
        return movieService.getAllMovies();
    }

}
