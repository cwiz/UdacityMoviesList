package com.example.surov.udacitymovielist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.surov.udacitymovielist.R;
import com.example.surov.udacitymovielist.activities.MovieDetailsActivity;
import com.example.surov.udacitymovielist.fragments.MovieItemFragment;
import com.example.surov.udacitymovielist.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final List<Movie> mValues;
    private final MovieItemFragment.OnFragmentInteractionListener mListener;

    public MovieListAdapter(List<Movie> movies, MovieItemFragment.OnFragmentInteractionListener listener) {
        mValues = movies;
        mListener = listener;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final Movie item = mValues.get(i);

        // TODO: set control values here
        Picasso.get()
                .load(item.getPosterFullPath())
                .resize(200, 300)
                .centerCrop()
                .into(holder.mMoviePoster);

        // TODO: set click event handlers here
        holder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mListener.onListFragmentInteraction(item);
                                            }
                                        }
        );
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mMoviePoster;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.mView = itemView;

            // TODO: set references here
            mMoviePoster = view.findViewById(R.id.image_movie_poster);
        }
    }

}
