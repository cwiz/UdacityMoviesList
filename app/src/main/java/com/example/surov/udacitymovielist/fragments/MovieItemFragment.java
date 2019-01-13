package com.example.surov.udacitymovielist.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.surov.udacitymovielist.models.Movie;

public class MovieItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    public interface OnFragmentInteractionListener {
        void onListFragmentInteraction(Movie movie);
    }
}
