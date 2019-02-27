package com.tmdb.movie.datasource.repository;


import com.tmdb.movie.datasource.model.MovieDetail;
import com.tmdb.movie.datasource.model.MoviesList;

import retrofit2.Call;

public interface DataSourceContract {

    Call<MoviesList> onFetchResult();

    Call<MovieDetail> getMovieDetail(int id);

}
