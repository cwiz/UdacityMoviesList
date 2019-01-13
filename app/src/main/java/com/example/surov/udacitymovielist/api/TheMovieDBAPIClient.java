package com.example.surov.udacitymovielist.api;

import android.net.Uri;

import com.example.surov.udacitymovielist.models.Movie;
import com.example.surov.udacitymovielist.models.MovieVideo;
import com.example.surov.udacitymovielist.settings.Settings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TheMovieDBAPIClient {

    private final String apiKey;
    private static String API_URL = "https://api.themoviedb.org/3";
    private final OkHttpClient httpClient;
    private final Gson gson;

    public TheMovieDBAPIClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Movie> listMovies(String sortBy) {
        String url = Uri.parse(API_URL).buildUpon()
                .appendPath("discover").appendPath("movie")
                .appendQueryParameter("api_key", Settings.THE_MOVIE_DB_API_KEY)
                .appendQueryParameter("sort_by", sortBy)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String rawJson;
            try {
                rawJson = response.body().string();
            } catch (IOException e) {
                return null;
            }
            ArrayList<Movie> movies;
            JSONObject object;
            try {
                object = new JSONObject(rawJson);
                movies = gson.fromJson(
                        object.get("results").toString(),
                        TypeToken.getParameterized(ArrayList.class, Movie.class).getType());
                return movies;
            } catch (JSONException e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public List<MovieVideo> getVideos(int movieId) {
        String url = Uri.parse(API_URL).buildUpon()
                .appendPath("movie").appendPath(Integer.toString(movieId)).appendPath("videos")
                .appendQueryParameter("api_key", Settings.THE_MOVIE_DB_API_KEY)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String rawJson;
            try {
                rawJson = response.body().string();
            } catch (IOException e) {
                return null;
            }
            ArrayList<MovieVideo> movies;
            JSONObject object;
            try {
                object = new JSONObject(rawJson);
                movies = gson.fromJson(
                        object.get("results").toString(),
                        TypeToken.getParameterized(ArrayList.class, MovieVideo.class).getType());
                return movies;
            } catch (JSONException e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public Movie getDetails(int movieId) {
        String url = Uri.parse(API_URL).buildUpon()
                .appendPath("movie").appendPath(Integer.toString(movieId))
                .appendQueryParameter("api_key", Settings.THE_MOVIE_DB_API_KEY)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String rawJson;
            try {
                rawJson = response.body().string();
            } catch (IOException e) {
                return null;
            }
            JSONObject object;
            try {
                object = new JSONObject(rawJson);
                return gson.fromJson(
                        object.toString(), Movie.class);
            } catch (JSONException e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
