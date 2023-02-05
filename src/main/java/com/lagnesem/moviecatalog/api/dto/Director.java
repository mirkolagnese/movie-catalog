package com.lagnesem.moviecatalog.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class Director {

    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;

    public Director(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
