package com.lagnesem.moviecatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.lagnesem.moviecatalog.api.dto.Movie;
import com.lagnesem.moviecatalog.api.dto.Rating;
import com.lagnesem.moviecatalog.infra.MovieRepository;
import com.lagnesem.moviecatalog.infra.RatingRepository;
import com.lagnesem.moviecatalog.infra.entity.MovieEntity;
import com.lagnesem.moviecatalog.infra.entity.RatingEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieCatalogServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private MovieCatalogService service;

    @Test
    void getAllMovies_twoItems() {
        //Arrange
        MovieEntity movie1 = mock(MovieEntity.class);
        when(movie1.getId()).thenReturn(1L);
        when(movie1.getTitle()).thenReturn("aTitle");
        MovieEntity movie2 = mock(MovieEntity.class);
        when(movie2.getId()).thenReturn(2L);
        when(movie2.getTitle()).thenReturn("anotherTitle");
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        //Act
        List<Movie> allMovies = service.getAllMovies();

        //Assert
        assertEquals(2, allMovies.size());
        assertEquals(1L, allMovies.get(0).getId());
        assertEquals("aTitle", allMovies.get(0).getTitle());
        assertEquals(2L, allMovies.get(1).getId());
        assertEquals("anotherTitle", allMovies.get(1).getTitle());
    }

    @Test
    void getAllMovies_noItems() {
        //Arrange
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        //Act
        List<Movie> allMovies = service.getAllMovies();

        //Assert
        assertEquals(0, allMovies.size());
    }

    @Test
    void getMoviesWithMinimumScore_twoItems_oneMatches() {
        //Arrange
        RatingEntity ratingEntity1 = mock(RatingEntity.class);
        when(ratingEntity1.getScore()).thenReturn(100);
        RatingEntity ratingEntity2 = mock(RatingEntity.class);
        RatingEntity ratingEntity3 = mock(RatingEntity.class);
        when(ratingEntity3.getScore()).thenReturn(12);
        MovieEntity movie1 = mock(MovieEntity.class);
        when(movie1.getId()).thenReturn(1L);
        when(movie1.getTitle()).thenReturn("aTitle");
        HashSet ratings1 = new HashSet();
        ratings1.add(ratingEntity1);
        ratings1.add(ratingEntity2);
        HashSet ratings2 = new HashSet();
        ratings2.add(ratingEntity3);
        when(movie1.getRatings()).thenReturn(ratings1);
        MovieEntity movie2 = mock(MovieEntity.class);
        when(movie2.getRatings()).thenReturn(ratings2);
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        //Act
        List<Movie> allMovies = service.getMoviesWithMinimumScore(30);

        //Assert
        assertEquals(1, allMovies.size());
        assertEquals(1L, allMovies.get(0).getId());
        assertEquals("aTitle", allMovies.get(0).getTitle());
    }

    @Test
    void getMovieById_found() {
        //Arrange
        MovieEntity movie1 = mock(MovieEntity.class);
        when(movie1.getId()).thenReturn(1L);
        when(movie1.getTitle()).thenReturn("aTitle");
        when(movieRepository.findById(eq(1L))).thenReturn(Optional.of(movie1));

        //Act
        Movie actual = service.getMovieById(1L);

        //Assert
        assertEquals(1L, actual.getId());
        assertEquals("aTitle", actual.getTitle());
    }

    @Test
    void getMovieById_notFound() {
        //Arrange
        when(movieRepository.findById(eq(1L))).thenReturn(Optional.empty());

        //Act
        Movie actual = service.getMovieById(1L);

        //Assert
        assertNull(actual);
    }

    @Test
    void getRatings_found() {
        //Arrange
        MovieEntity movie1 = mock(MovieEntity.class);
        RatingEntity ratingEntity1 = mock(RatingEntity.class);
        when(ratingEntity1.getScore()).thenReturn(50);
        HashSet ratings1 = new HashSet();
        ratings1.add(ratingEntity1);
        when(movie1.getRatings()).thenReturn(ratings1);
        when(movieRepository.findById(eq(1L))).thenReturn(Optional.of(movie1));

        //Act
        Set<Rating> actual = service.getRatings(1L);

        //Assert
        assertEquals(1L, actual.size());
        //Assert that the set of ratings contains an item with the expected score
        assertEquals(true, actual.removeIf(x -> 50 == x.getScore()));
    }


    @Test
    void getRatings_ratingNotFound() {
        //Arrange
        MovieEntity movie1 = mock(MovieEntity.class);
        HashSet ratings1 = new HashSet();
        when(movie1.getRatings()).thenReturn(ratings1);
        when(movieRepository.findById(eq(1L))).thenReturn(Optional.of(movie1));

        //Act
        Set<Rating> actual = service.getRatings(1L);

        //Assert
        assertEquals(0, actual.size());
    }

    @Test
    void getRatings_movieNotFound() {
        //Arrange
        when(movieRepository.findById(eq(1L))).thenReturn(Optional.empty());

        //Act
        Set<Rating> actual = service.getRatings(1L);

        //Assert
        assertNull(actual);
    }
}
