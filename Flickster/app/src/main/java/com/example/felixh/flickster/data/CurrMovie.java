package com.example.felixh.flickster.data;

import com.example.felixh.flickster.models.Movie;

/**
 * Created by Felix Huang on 9/17/2017.
 */

public class CurrMovie {
    public static void setCurrentMovie(Movie currentMovie) {
        CurrMovie.currentMovie = currentMovie;
    }

    public static Movie currentMovie;

    public static Movie getCurrentMovie() {
        return currentMovie;
    }
}
