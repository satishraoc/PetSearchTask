package com.tmdb.movie.datasource.repository;

import com.tmdb.movie.constant.Constants;
import com.tmdb.movie.datasource.model.MovieDetail;
import com.tmdb.movie.datasource.model.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TmdbClient {

    @GET(Constants.UrlConstant.MOVIE_LIST_URL)
    Call<MoviesList> getMovieList();

    @GET(Constants.UrlConstant.MOVIE_DETAIL_URL)
    Call<MovieDetail> getMovieDetail(@Path("movieId") int id);

}
