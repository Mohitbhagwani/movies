package com.movies.test.Repository;

import com.movies.test.DTO.GenreMovieDTO;
import com.movies.test.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query(value = "SELECT * FROM movies m ORDER BY m.runtime_minutes DESC LIMIT 10",nativeQuery = true)
    List<Movie> findTop10ByOrderByRuntimeMinutesDesc();

    @Query(value = "\n" +
            "SELECT m.tconst, m.primary_title, m.genres, AVG(CAST(r.average_rating AS DECIMAL(10, 2))) AS averageRating\n" +
            "FROM movies m\n" +
            "JOIN ratings r ON m.tconst = r.tconst\n" +
            "GROUP BY m.tconst, m.primary_title, m.genres\n" +
            "HAVING AVG(CAST(r.average_rating AS DECIMAL(10, 2))) > 6.0\n" +
            "ORDER BY averageRating DESC", nativeQuery = true)
    List<Object[]> findTopRatedMovies();

    @Query(value = "SELECT genre, primaryTitle, CAST(numVotes AS DECIMAL) AS numVotes " +
            "FROM ( " +
            "    SELECT m.genres AS genre, m.primary_title AS primaryTitle, CAST(r.num_votes AS DECIMAL) AS numVotes, '0' AS sortKey " +
            "    FROM movies m " +
            "    JOIN ratings r ON m.tconst = r.tconst " +
            "    GROUP BY m.genres, m.primary_title " +
            "    UNION ALL " +
            "    SELECT m.genres AS genre, 'TOTAL' AS primaryTitle, CAST(SUM(CAST(r.num_votes AS DECIMAL)) AS DECIMAL) AS numVotes, '1' AS sortKey " +
            "    FROM movies m " +
            "    JOIN ratings r ON m.tconst = r.tconst " +
            "    GROUP BY m.genres " +
            ") AS subquery " +
            "ORDER BY genre, sortKey, primaryTitle", nativeQuery = true)
    List<Object[]> findGenreMoviesWithSubtotals();



}


