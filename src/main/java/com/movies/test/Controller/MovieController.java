package com.movies.test.Controller;

import com.movies.test.DTO.GenreMovieDTO;
import com.movies.test.DTO.MovieDTO;
import com.movies.test.DTO.TopRatedMovieDTO;
import com.movies.test.Entity.Movie;
import com.movies.test.Repository.MovieRepository;
import com.movies.test.Service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MovieController {
    private final MovieRepository movieRepository;
    private final MovieService movieService;
    private final JdbcTemplate jdbcTemplate;


    public MovieController(MovieRepository movieRepository, MovieService movieService, JdbcTemplate jdbcTemplate) {
        this.movieRepository = movieRepository;
        this.movieService = movieService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/longest-duration-movies")
    public List<Movie> getLongestDurationMovies() {
        List<Movie> movies = movieRepository.findTop10ByOrderByRuntimeMinutesDesc();
        return movies;
    }

    @PostMapping("/new-movie")
    public String createNewMovie(@RequestBody MovieDTO newMovieDTO) {
        Movie movie = new Movie();
        // Set the values from the DTO to the movie entity
        movie.setTconst(newMovieDTO.getTconst());
        movie.setTitleType(newMovieDTO.getTitleType());
        movie.setPrimaryTitle(newMovieDTO.getPrimaryTitle());
        movie.setRuntimeMinutes(newMovieDTO.getRuntimeMinutes());
        movie.setGenres(newMovieDTO.getGenres());

        movieRepository.save(movie);

        return "success";
    }

    @GetMapping("/top-rated-movies")
    public ResponseEntity<List<TopRatedMovieDTO>> getTopRatedMovies() {
        List<TopRatedMovieDTO> topRatedMovies = movieService.getTopRatedMovies();
        return ResponseEntity.ok(topRatedMovies);
    }


    @GetMapping("/genre-movies-with-subtotals")
    public List<GenreMovieDTO> getGenreMoviesWithSubtotals() {
        return movieService.getGenreMoviesWithSubtotals();
    }

    @PostMapping("/api/v1/update-runtime-minutes")
    public String incrementRuntimeMinutes(@RequestParam String genre) {
       try {
           String sql = "UPDATE movies " +
                   "SET runtime_minutes = runtime_minutes + " +
                   "CASE " +
                   "WHEN genres = 'Documentary' THEN 15 " +
                   "WHEN genres = 'Animation' THEN 30 " +
                   "ELSE 45 " +
                   "END " +
                   "WHERE genres = ?";



           jdbcTemplate.update(sql, genre);
       }
       catch (Exception e){
           log.info("Exception occured at update {}",e);
           return "failed";
       }
       return"success";

    }

}
