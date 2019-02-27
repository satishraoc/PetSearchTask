package com.tmdb.movie.datasource.repository;

import com.tmdb.movie.datasource.model.MovieDetail;
import com.tmdb.movie.datasource.model.MoviesList;
import com.tmdb.movie.dependencies.DaggerNetworkComponent;

import javax.inject.Inject;

import retrofit2.Call;

public class DataSource implements DataSourceContract {

    @Inject
    TmdbClient tmdbClient;


    public DataSource() {
        DaggerNetworkComponent.builder().build().inject(this);
    }


    @Override
    public Call<MoviesList> onFetchResult() {
        return tmdbClient.getMovieList();
    }

    @Override
    public Call<MovieDetail> getMovieDetail(int id) {
        return tmdbClient.getMovieDetail(id);
    }


}
