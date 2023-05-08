package com.movies.test.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GenreMovieDTO {
    private String genre;
    private String primaryTitle;
    private BigDecimal numVotes;
}
