package com.example.surov.udacitymovielist.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.surov.udacitymovielist.R;
import com.example.surov.udacitymovielist.adapters.MovieListAdapter;
import com.example.surov.udacitymovielist.api.TheMovieDBAPIClient;
import com.example.surov.udacitymovielist.models.Movie;
import com.example.surov.udacitymovielist.settings.Settings;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    // Controls
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Code
    private TheMovieDBAPIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        setTitle("Popular movies");

        // setup API client
        apiClient = new TheMovieDBAPIClient(Settings.THE_MOVIE_DB_API_KEY);

        // setup views and controls
        mProgressBar = findViewById(R.id.pg_progress_bar);

        // Setup Recycler view
        mRecyclerView = findViewById(R.id.rv_movie_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // request data
        requestMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Custom view methods
    private void requestMovies(){
        new RequestMoviesAsyncTask().execute();
    }

    private void setResults(List<Movie> movies) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new MovieListAdapter(movies);
        mRecyclerView.setAdapter(mAdapter);
    }

    class RequestMoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>>{
        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return apiClient.listMovies("");
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            setResults(movies);
        }
    }
}
