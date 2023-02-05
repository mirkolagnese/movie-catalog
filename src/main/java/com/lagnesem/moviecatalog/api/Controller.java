package com.lagnesem.moviecatalog.api;

import com.lagnesem.moviecatalog.service.DirectorService;
import com.lagnesem.moviecatalog.service.MovieCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final MovieCatalogService movieService;
    private final DirectorService directorService;

    @Autowired
    public Controller(MovieCatalogService movieService, DirectorService directorService) {
        this.movieService = movieService;
        this.directorService = directorService;
    }

//    @Transactional
//    @GetMapping("/do-something")
//    public List<MovieEntity> doSomething() {
//        IntStream.rangeClosed(1, 10)
//                .forEach(x -> {
//                    DirectorEntity director = new DirectorEntity();
//                    director.setName(RandomStringUtils.random(10, true, false));
//                    MovieEntity movie = new MovieEntity();
//                    Set<RatingEntity> ratings = new HashSet<RatingEntity>();
//                    for (int i = 0; i < 4; i++) {
//                        RatingEntity rating = new RatingEntity();
//                        rating.setScore(ThreadLocalRandom.current().nextInt(0, 101));
//                        rating.setComment("This is a test comment");
//                        ratings.add(rating);
//                    }
//                    movie.setRatings(ratings);
//                    movie.setTitle(String.format("Movie %s", RandomStringUtils.random(10, true, false)));
//                    Set<DirectorEntity> directors = new HashSet<>();
//                    directors.add(director);
//                    movie.setDirectors(directors);
//                    movieService.saveMovie(movie);
//                });
//
//        List<MovieEntity> allMovies = movieService.getAllMovies();
//        MovieEntity movie = allMovies.get(0);
//        movieService.deleteMovie(movie.getId());
//        return movieService.getAllMovies();
//    }

}
