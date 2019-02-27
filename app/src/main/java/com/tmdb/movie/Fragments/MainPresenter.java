package com.tmdb.movie.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.tmdb.movie.datasource.model.MoviesList;
import com.tmdb.movie.datasource.model.Result;
import com.tmdb.movie.datasource.repository.DataSourceContract;
import com.tmdb.movie.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private DataSourceContract mDataSourceContract;
    private Context mContext;

    public MainPresenter(MainContract.View view, DataSourceContract dataSourceContract, Context context) {
        this.mView = view;
        this.mDataSourceContract = dataSourceContract;
        this.mContext = context;
    }

    @Override
    public void onSnackbarRetry() {
        fetchMovieList();
        mView.updateProgressBar(View.VISIBLE);
    }

    @Override
    public void onStart() {
        fetchMovieList();
        mView.updateProgressBar(View.VISIBLE);
    }

    private void fetchMovieList() {
        mDataSourceContract.onFetchResult().enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(@NonNull Call<MoviesList> call, @NonNull Response<MoviesList> response) {
                if (response.body() != null) {
                    List<Result> results = response.body().getResults();
                    mView.showResult(results);
                }
                mView.updateProgressBar(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<MoviesList> call, @NonNull Throwable t) {
                if (!NetworkUtils.internetCheck(mContext)) {
                    mView.showSnackBarMessage("Internet not available", true);
                } else {
                    mView.showSnackBarMessage("Network Error! Please try again", true);
                }
                mView.updateProgressBar(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(int movieId,String movieName) {
        mView.openDetailPage(movieId,movieName);
    }
}
