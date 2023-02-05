package com.lagnesem.moviecatalog.api;

import com.lagnesem.moviecatalog.api.dto.Director;
import com.lagnesem.moviecatalog.api.dto.Movie;
import com.lagnesem.moviecatalog.api.dto.Rating;
import com.lagnesem.moviecatalog.service.MovieCatalogService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping
public class MovieCatalogController {

    private final MovieCatalogService movieCatalogService;

    @Autowired
    public MovieCatalogController(MovieCatalogService movieCatalogService) {
        this.movieCatalogService = movieCatalogService;
    }


    @GetMapping("/movies")
    public List<Movie> getAllMovies(@RequestParam(name = "minimum-score") Optional<Integer> minimumScore) {
        return minimumScore.isPresent() ? movieCatalogService.getMoviesWithMinimumScore(minimumScore.get())
                : movieCatalogService.getAllMovies();
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id) {
        Movie result = movieCatalogService.getMovieById(id);
        return new ResponseEntity<>(result, result == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/movies/{id}/ratings")
    public ResponseEntity<Set<Rating>> getRatingsByMovieId(@PathVariable("id") Long id) {
        Set<Rating> result = movieCatalogService.getRatings(id);
        return new ResponseEntity<>(result, result == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("/movies/{id}/ratings")
    public ResponseEntity addRating(@PathVariable("id") Long movieId, @Validated @RequestBody Rating rating) {
        boolean result = movieCatalogService.addRating(movieId, rating);
        return new ResponseEntity<>(result ? HttpStatus.CREATED : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/movies/{movieId}/ratings/{ratingId}")
    public ResponseEntity updateRating(@PathVariable("movieId") Long movieId, @PathVariable("ratingId") Long ratingId,
            @Validated @RequestBody Rating rating) {
        boolean result = movieCatalogService.updateRating(movieId, ratingId, rating);
        return new ResponseEntity<>(result ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/movies/{movieId}/ratings/{ratingId}")
    public ResponseEntity deleteRating(@PathVariable("movieId") Long movieId, @PathVariable("ratingId") Long ratingId) {
        boolean result = movieCatalogService.deleteRating(movieId, ratingId);
        return new ResponseEntity<>(result ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/movies/{id}/directors")
    public ResponseEntity<Set<Director>> getDirectorsByMovieId(@PathVariable("id") Long id) {
        Set<Director> result = movieCatalogService.getDirectorsByMovieId(id);
        return new ResponseEntity<>(result, result == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/directors")
    public ResponseEntity<List<Director>> getAllDirectors() {
        return new ResponseEntity<>(movieCatalogService.getAllDirectors(), HttpStatus.OK);
    }

    @PostMapping("/directors")
    public ResponseEntity<Long> saveDirector(@Validated @RequestBody Director director) {
        return new ResponseEntity<>(movieCatalogService.saveDirector(director), HttpStatus.CREATED);
    }

    @PostMapping("/movies")
    public ResponseEntity<Long> saveMovie(@Validated @RequestBody Movie movie) {
        return new ResponseEntity<>(movieCatalogService.saveMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("/directors/{id}/movies")
    public ResponseEntity<Set<Movie>> getAllMoviesByDirectorId(@PathVariable("id") Long id) {
        Set<Movie> result = movieCatalogService.getAllMoviesByDirectorId(id);
        return new ResponseEntity<>(result, result == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

//    @PostMapping
//    public Long saveMovie(@Validated @RequestBody MovieEntity movie) {
//        return movieService.saveMovie(movie);
//    }
//
//    @PutMapping("/{id}")
//    public Long updateMovie(@PathVariable("id") Long id, @Validated @RequestBody MovieEntity movie) {
//        movieService.updateMovie(movie);
//        return null;
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteMovie(@PathVariable("id") Long id) {
//        movieService.deleteMovie(id);
//    }
}
