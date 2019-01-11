package com.example.surov.udacitymovielist.api;

import org.junit.Test;

import com.example.surov.udacitymovielist.models.Movie;
import com.example.surov.udacitymovielist.settings.Settings;

import static org.junit.Assert.*;

import java.util.List;

public class TheMovieDBAPIClientTest {

    private TheMovieDBAPIClient api;

    public TheMovieDBAPIClientTest() {
        this.api = new TheMovieDBAPIClient(Settings.THE_MOVIE_DB_API_KEY);
    }

    @Test
    public void listMovies() {
        List<Movie> movieList = api.listMovies("popularity.desc");
        assertNotEquals(movieList, null);
        assertNotEquals(movieList.size(), 0);

    }
}