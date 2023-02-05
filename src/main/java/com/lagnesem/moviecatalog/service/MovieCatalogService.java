package com.lagnesem.moviecatalog.service;

import com.lagnesem.moviecatalog.api.dto.Director;
import com.lagnesem.moviecatalog.api.dto.Movie;
import com.lagnesem.moviecatalog.api.dto.Rating;
import com.lagnesem.moviecatalog.infra.DirectorRepository;
import com.lagnesem.moviecatalog.infra.MovieRepository;
import com.lagnesem.moviecatalog.infra.RatingRepository;
import com.lagnesem.moviecatalog.infra.entity.DirectorEntity;
import com.lagnesem.moviecatalog.infra.entity.MovieEntity;
import com.lagnesem.moviecatalog.infra.entity.RatingEntity;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieCatalogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieCatalogService.class);

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public MovieCatalogService(MovieRepository movieRepository, DirectorRepository directorRepository,
            RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     * Retrieves all movies. Only titles are populated for ease of readability
     *
     * @return a list of all movies
     */
    public List<Movie> getAllMovies() {
        LOGGER.info("Fetching all movies");
        List<MovieEntity> allEntities = movieRepository.findAll();
        return allEntities.stream().map(m -> {
            LOGGER.info("Movie with ID {} fetched", m.getId());
            Movie movie = new Movie(m.getId(), m.getTitle(), null, null);
            return movie;
        }).collect(Collectors.toList());
    }

    /**
     * Returns a list of movies with at least a rating with a score equal or higher than the value passed in input
     *
     * @param score
     * @return
     */
    public List<Movie> getMoviesWithMinimumScore(int score) {
        LOGGER.info("Fetching all movies with score equal or greather than {}", score);
        List<MovieEntity> allEntities = movieRepository.findAll();
        return allEntities.stream().filter(m ->
                m.getRatings().stream().anyMatch(r -> {
                    boolean isHigher = r.getScore() >= score;
                    LOGGER.info("Movie {} - rating with score {} fetched - {}", m.getId(), r.getScore(), isHigher);
                    return isHigher;
                })
        ).map(m -> {
            LOGGER.info("Movie with ID {} fetched", m.getId());
            Movie movie = new Movie(m.getId(), m.getTitle(), null, null);
            return movie;
        }).collect(Collectors.toList());
    }

    /**
     * Returns details about a specific movie. Ratings are not returned, as there is a specific set of endpoints to
     * cater for them.
     *
     * @param id the movieId
     * @return
     */
    public Movie getMovieById(Long id) {
        Optional<MovieEntity> entityOptional = movieRepository.findById(id);
        if (!entityOptional.isPresent()) {
            return null;
        }
        MovieEntity movieEntity = entityOptional.get();
        Set<Director> directors = movieEntity.getDirectors().stream().map(d -> new Director(d.getId(), d.getName()))
                .collect(Collectors.toSet());
        return new Movie(movieEntity.getId(), movieEntity.getTitle(), null, directors);
    }

    /**
     * Returns all the ratings for a specific movie
     *
     * @param movieId
     * @return
     */
    public Set<Rating> getRatings(Long movieId) {
        Optional<MovieEntity> entityOptional = movieRepository.findById(movieId);
        if (!entityOptional.isPresent()) {
            return null;
        }
        MovieEntity movieEntity = entityOptional.get();
        if (movieEntity.getRatings() == null) {
            return Collections.EMPTY_SET;
        }
        return movieEntity.getRatings().stream()
                .map(r -> new Rating(r.getId(), r.getScore(), r.getComment())).collect(
                        Collectors.toSet());
    }

    /**
     * Returns all the directors for a specific movie
     *
     * @param movieId
     * @return set of directors
     */
    public Set<Director> getDirectorsByMovieId(Long movieId) {
        Optional<MovieEntity> entityOptional = movieRepository.findById(movieId);
        if (!entityOptional.isPresent()) {
            return null;
        }
        MovieEntity movieEntity = entityOptional.get();
        if (movieEntity.getRatings() == null) {
            return Collections.EMPTY_SET;
        }
        return movieEntity.getDirectors().stream()
                .map(d -> new Director(d.getId(), d.getName())).collect(
                        Collectors.toSet());
    }

    /**
     * Returns all the directors
     *
     * @return set of directors
     */
    public List<Director> getAllDirectors() {
        LOGGER.info("Fetching all movies");
        List<DirectorEntity> allEntities = directorRepository.findAll();
        return allEntities.stream().map(m -> {
            LOGGER.info("Director with ID {} fetched", m.getId());
            Director director = new Director(m.getId(), m.getName());
            return director;
        }).collect(Collectors.toList());
    }

    /**
     * Gets all the movies by director Id
     */
    public Set<Movie> getAllMoviesByDirectorId(Long directorId) {
        Optional<DirectorEntity> entityOptional = directorRepository.findById(directorId);
        if (!entityOptional.isPresent()) {
            return null;
        }
        Set<MovieEntity> allEntities = entityOptional.get().getMovies();
        return allEntities.stream().map(m -> {
            LOGGER.info("Movie with ID {} fetched", m.getId());
            Movie movie = new Movie(m.getId(), m.getTitle(), null, null);
            return movie;
        }).collect(Collectors.toSet());
    }

    /**
     * Adds a rating to an existing movie. Returns false if the movie is not present, true if the rating is successfully
     * added.
     *
     * @param movieId
     * @param rating
     * @return
     */
    public boolean addRating(Long movieId, Rating rating) {
        Optional<MovieEntity> entityOptional = movieRepository.findById(movieId);
        if (!entityOptional.isPresent()) {
            return false;
        }
        MovieEntity movieEntity = entityOptional.get();
        Set<RatingEntity> ratings = movieEntity.getRatings();
        if (ratings == null) {
            ratings = new HashSet<>();
        }
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setComment(rating.getComment());
        ratingEntity.setScore(rating.getScore());
        ratings.add(ratingEntity);
        movieEntity.setRatings(ratings);
        movieRepository.saveAndFlush(movieEntity);
        return true;
    }

    /**
     * Updates a rating of an existing movie.
     *
     * @param movieId
     * @param rating
     * @return false if the movie or the rating is not present, true if the rating is successfully updated.
     */
    public boolean updateRating(Long movieId, Long ratingId, Rating rating) {
        Optional<MovieEntity> entityOptional = movieRepository.findById(movieId);
        if (!entityOptional.isPresent()) {
            return false;
        }

        MovieEntity movieEntity = entityOptional.get();
        Set<RatingEntity> ratings = movieEntity.getRatings();
        if (ratings == null) {
            return false;
        }
        Optional<RatingEntity> ratingEntityOptional = ratings.stream().filter(r -> ratingId == r.getId()).findFirst();
        if (!ratingEntityOptional.isPresent()) {
            return false;
        }
        RatingEntity ratingEntity = ratingEntityOptional.get();
        ratingEntity.setComment(rating.getComment());
        ratingEntity.setScore(rating.getScore());
        ratings.add(ratingEntity);
        ratingRepository.saveAndFlush(ratingEntity);
        return true;
    }

    /**
     * Deletes a rating from a movie, if present
     *
     * @param movieId
     * @param ratingId
     * @return true if rating is successfully deleted, else false
     */
    public boolean deleteRating(Long movieId, Long ratingId) {
        Optional<MovieEntity> entityOptional = movieRepository.findById(movieId);
        if (!entityOptional.isPresent()) {
            return false;
        }

        MovieEntity movieEntity = entityOptional.get();
        Set<RatingEntity> ratings = movieEntity.getRatings();
        if (ratings == null) {
            return false;
        }
        boolean isDeleted = ratings.removeIf(r -> ratingId == r.getId());
        movieRepository.saveAndFlush(movieEntity);
        return isDeleted;
    }

    /**
     * So far, for test purposes
     *
     * @param movie
     */
    public void saveMovie(MovieEntity movie) {
        movieRepository.saveAndFlush(movie);
    }

}
