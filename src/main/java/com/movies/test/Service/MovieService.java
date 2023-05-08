package com.movies.test.Service;

import com.movies.test.DTO.GenreMovieDTO;
import com.movies.test.DTO.TopRatedMovieDTO;
import com.movies.test.Repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public List<TopRatedMovieDTO> getTopRatedMovies() {
        List<Object[]> movieData = movieRepository.findTopRatedMovies();
        List<TopRatedMovieDTO> topRatedMovies = new ArrayList<>();

        for (Object[] data : movieData) {
            TopRatedMovieDTO movieDTO = new TopRatedMovieDTO();
            movieDTO.setTconst((String) data[0]);
            movieDTO.setPrimaryTitle((String) data[1]);
            movieDTO.setGenre((String) data[2]);
            movieDTO.setAverageRating((BigDecimal) data[3]);
            topRatedMovies.add(movieDTO);
        }

        return topRatedMovies;
    }
    public List<GenreMovieDTO> getGenreMoviesWithSubtotals() {
        List<Object[]> queryResult = movieRepository.findGenreMoviesWithSubtotals();
        List<GenreMovieDTO> genreMovies = new ArrayList<>();

        for (Object[] result : queryResult) {
            GenreMovieDTO genreMovie = new GenreMovieDTO();
            genreMovie.setGenre((String) result[0]);
            genreMovie.setPrimaryTitle((String) result[1]);
            genreMovie.setNumVotes((BigDecimal) result[2]);

            genreMovies.add(genreMovie);
        }

        return genreMovies;
    }

}
