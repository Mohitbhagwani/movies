# movies
Interview test for project movies


DROP TABLE if exists MOVIES; 
//to drop the table


CREATE TABLE movies AS SELECT * FROM CSVREAD('/home/mohit/Desktop/test_interview/test/src/main/resources/movies.csv');
SELECT * FROM MOVIES ; 

DROP TABLE if exists ratings; 
CREATE TABLE ratings AS SELECT * FROM CSVREAD('/home/mohit/Desktop/test_interview/test/src/main/resources/ratings.csv');
SELECT * FROM ratings ; 


SELECT m.tconst, m.primary_title, m.genres, AVG(CAST(r.average_rating AS DECIMAL(10, 2))) AS averageRating
FROM movies m
JOIN ratings r ON m.tconst = r.tconst
GROUP BY m.tconst, m.primary_title, m.genres
HAVING AVG(CAST(r.average_rating AS DECIMAL(10, 2))) > 6.0
ORDER BY averageRating DESC;

SELECT genre, primaryTitle, numVotes
FROM (
    SELECT m.genres AS genre, m.primary_title AS primaryTitle, r.num_votes::numeric AS numVotes, '0' AS sortKey
    FROM movies m
    JOIN ratings r ON m.tconst = r.tconst
    GROUP BY m.genres, m.primary_title
    UNION ALL
    SELECT m.genres AS genre, 'TOTAL' AS primaryTitle, SUM(r.num_votes::numeric) AS numVotes, '1' AS sortKey
    FROM movies m
    JOIN ratings r ON m.tconst = r.tconst
    GROUP BY m.genres
) AS subquery
ORDER BY genre, sortKey, primaryTitle

