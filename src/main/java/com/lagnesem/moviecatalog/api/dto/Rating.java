package com.lagnesem.moviecatalog.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Rating {

    private Long id;

    private int score;

    private String comment;

    public Rating(Long id, int score, String comment) {
        this.id = id;
        this.score = score;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }
}
