package com.tmdb.movie.Fragments;

import com.tmdb.movie.baseFragment.BasePresenter;
import com.tmdb.movie.baseFragment.BaseView;
import com.tmdb.movie.datasource.model.Result;

import java.util.List;

public interface MainContract {

    interface Presenter extends BasePresenter {
        void onItemClick(int movieId,String movieName);
    }

    interface View extends BaseView {

        void showResult(List<Result> moviesLists);
        void openDetailPage(int movieId,String movieName);

    }

}
