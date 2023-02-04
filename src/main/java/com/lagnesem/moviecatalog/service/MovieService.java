package com.lagnesem.moviecatalog.service;

import com.lagnesem.moviecatalog.dto.Movie;
import com.lagnesem.moviecatalog.infra.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.saveAndFlush(movie);
    }

    public Movie updateMovie(Movie movie) {
        Optional<Movie> fromDb = movieRepository.findById(movie.getId());
        if (fromDb.isPresent()) {
            Movie storedObj = fromDb.get();
            storedObj.setDirector(movie.getDirector());
            storedObj.setRatings(movie.getRatings());
            return movieRepository.saveAndFlush(storedObj);
        }
        return null;
    }

    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    public List<Movie> getMoviesWithRatingHigherThan(int rating) {
        return null;
    }
}
