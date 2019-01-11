package com.example.surov.udacitymovielist.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.surov.udacitymovielist.R;
import com.example.surov.udacitymovielist.models.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<Movie> mValues;

    public MovieListAdapter(List<Movie> movies) {
        mValues = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.fragment_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.mItem = mValues.get(i);

        // TODO: set control values here
        holder.mMovieTitle.setText(mValues.get(i).getTitle());

        // TODO: set click event handlers here
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Movie mItem;
        public final TextView mMovieTitle;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.mView = itemView;

            // TODO: set references here
            mMovieTitle = view.findViewById(R.id.tv_movie_title);
        }
    }

}
