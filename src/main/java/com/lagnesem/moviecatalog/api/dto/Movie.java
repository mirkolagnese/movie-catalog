package com.lagnesem.moviecatalog.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Set;
import javax.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class Movie {

    private Long id;
    @NotNull(message = "Title cannot be null")
    private String title;
    private Set<Rating> ratings;

    @NotNull(message = "A movie must have at least one director")
    private Set<Director> directors;

    public Movie(Long id, String title, Set<Rating> ratings, Set<Director> directors) {
        this.id = id;
        this.title = title;
        this.ratings = ratings;
        this.directors = directors;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Set<Director> getDirectors() {
        return directors;
    }
}
