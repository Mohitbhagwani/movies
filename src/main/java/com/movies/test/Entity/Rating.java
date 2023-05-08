package com.movies.test.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @Column(name = "tconst")
    private String tconst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tconst", referencedColumnName = "tconst", insertable = false, updatable = false)
    private Movie movie;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "num_votes")
    private Integer numVotes;
}

