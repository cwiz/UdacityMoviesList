package com.example.surov.udacitymovielist.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surov.udacitymovielist.R;
import com.example.surov.udacitymovielist.api.TheMovieDBAPIClient;
import com.example.surov.udacitymovielist.models.Movie;
import com.example.surov.udacitymovielist.settings.Settings;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    // Controls
    ImageView mMoviePoster;
    TextView mMovieLength;
    TextView mMovieRating;
    TextView mMovieDescription;

    // Code
    private TheMovieDBAPIClient apiClient;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setup API client
        apiClient = new TheMovieDBAPIClient(Settings.THE_MOVIE_DB_API_KEY);

        // setup views and controls
        mMovieLength = findViewById(R.id.tv_movie_length);
        mMovieRating = findViewById(R.id.tv_movie_rating);
        mMovieDescription = findViewById(R.id.tv_movie_description);
        mMoviePoster = findViewById(R.id.image_iv);

        Intent intentThatCreatedThisActivity = getIntent();

        // request data
        if (intentThatCreatedThisActivity.hasExtra("movie_id")){
            movieId = intentThatCreatedThisActivity.getIntExtra("movie_id", 254);
            requestMovieDetails();
        }

        setTitle("Loading...");
    }

    // Custom view methods
    private void requestMovieDetails(){
        new RequestMovieDetailsAsyncTask().execute();
    }

    private void setMovie(Movie movie) {

        mMovieLength.setText(Integer.toString(movie.getRuntime()));
        mMovieRating.setText(movie.getVoteAverage());
        mMovieDescription.setText(movie.getOverview());

        Picasso.get()
                .load(movie.getPosterFullPath())
                .resize(600, 900)
                .centerCrop()
                .into(mMoviePoster);

        setTitle(movie.getTitle());

    }

    class RequestMovieDetailsAsyncTask extends AsyncTask<Void, Void, Movie> {

        @Override
        protected Movie doInBackground(Void... voids) {
            return apiClient.getDetails(movieId);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            setMovie(movie);
        }
    }

}
