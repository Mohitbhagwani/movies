package com.movies.test.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopRatedMovieDTO {
    private String tconst;
    private String primaryTitle;
    private String genre;
    private BigDecimal averageRating;

}
