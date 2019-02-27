package com.tmdb.movie.Fragments;

import com.tmdb.movie.baseFragment.BasePresenter;
import com.tmdb.movie.baseFragment.BaseView;
import com.tmdb.movie.datasource.model.MovieDetail;

public interface FullDetailContract {

    interface Presenter extends BasePresenter{

    }

    interface View extends BaseView{

        void setMovieDetails(MovieDetail movieDetails);

    }
}
