package com.tmdb.movie.constant;

public class Constants {

    public static class UrlConstant {
        public static final String BASE_URL = "https://api.themoviedb.org";
        public static final String API_KEY = "567228de23e6ee9aebe38c25a4d9ec1a";
        public static final String MOVIE_LIST_URL = "/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY;
        public static final String MOVIE_DETAIL_URL = "3/movie/{movieId}?api_key=" + API_KEY + "&language=en-US";
        public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w300_and_h450_bestv2";
    }
    public static class MovieBundle {
        public static final String MOVIE_ID = "MOVIE_ID";
        public static final String MOVIE_NAME = "MOVIE_NAME";
    }
}
