package com.tmdb.movie.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.tmdb.movie.datasource.model.MovieDetail;
import com.tmdb.movie.datasource.repository.DataSourceContract;
import com.tmdb.movie.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullDetailPresenter implements FullDetailContract.Presenter {

    private FullDetailContract.View mView;
    private DataSourceContract mDataSourceContract;
    private Context mContext;
    private int movieId;

    public FullDetailPresenter(FullDetailContract.View mView, DataSourceContract mDataSourceContract, Context mContext, int movieId) {
        this.mView = mView;
        this.mDataSourceContract = mDataSourceContract;
        this.mContext = mContext;
        this.movieId = movieId;
    }

    @Override
    public void onSnackbarRetry() {
        fetchMovieDetails(movieId);
        mView.updateProgressBar(View.VISIBLE);
    }

    @Override
    public void onStart() {
        fetchMovieDetails(movieId);
        mView.updateProgressBar(View.VISIBLE);
    }

    private void fetchMovieDetails(int id) {
        mDataSourceContract.getMovieDetail(id).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                if (response.body() != null) {
                    mView.setMovieDetails(response.body());
                }else{
                    mView.showSnackBarMessage("Network Error! Please try again", true);

                }
                mView.updateProgressBar(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable t) {
                if (!NetworkUtils.internetCheck(mContext)) {
                    mView.showSnackBarMessage("Internet not available", true);
                } else {
                    mView.showSnackBarMessage("Network Error! Please try again", true);
                }
                mView.updateProgressBar(View.GONE);
            }
        });
    }

}
