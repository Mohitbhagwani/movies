package com.movies.test.DTO;

import lombok.Data;

@Data
public class MovieDTO {
    private String tconst;
    private String primaryTitle;
    private Integer runtimeMinutes;
    private String genres;
    private String titleType;
}
