package com.example.surov.udacitymovielist.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.surov.udacitymovielist.R;
import com.example.surov.udacitymovielist.adapters.MovieListAdapter;
import com.example.surov.udacitymovielist.api.TheMovieDBAPIClient;
import com.example.surov.udacitymovielist.fragments.MovieItemFragment;
import com.example.surov.udacitymovielist.models.Movie;
import com.example.surov.udacitymovielist.settings.Settings;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    // Controls
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;

    // Code
    private String movieSortOrder = "popular";
    private TheMovieDBAPIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        setTitle("Popular movies");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // setup API client
        apiClient = new TheMovieDBAPIClient(Settings.THE_MOVIE_DB_API_KEY);

        // setup views and controls
        mProgressBar = findViewById(R.id.pg_progress_bar);

        // Setup Recycler view
        mRecyclerView = findViewById(R.id.rv_movie_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // request data
        requestMovies();

        // title
        setTitle("Movie List");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            String currentTitle = (String) item.getTitle();

            if (currentTitle.equals("popular")) {
                item.setTitle("highest rated");
            } else {
                item.setTitle("popular");
            }

            setMovieSortOrder((String) item.getTitle());
        }

        // request data
        requestMovies();

        return false;
    }

    // Custom view methods
    private void requestMovies(){
        new RequestMoviesAsyncTask().execute();
    }

    private String getMovieSortOrder(){
        return movieSortOrder;
    }

    private void setMovieSortOrder(String order){
        movieSortOrder = order;
    }

    private void setResults(List<Movie> movies) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new MovieListAdapter(movies, new MovieItemFragmentInteractionListener());
        mRecyclerView.setAdapter(mAdapter);
    }

    class RequestMoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>>{
        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return apiClient.listMovies(getMovieSortOrder());
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            setResults(movies);
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            super.onPreExecute();
        }
    }

    class MovieItemFragmentInteractionListener implements MovieItemFragment.OnFragmentInteractionListener {

        @Override
        public void onListFragmentInteraction(Movie movie) {
            Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        }
    }
}
