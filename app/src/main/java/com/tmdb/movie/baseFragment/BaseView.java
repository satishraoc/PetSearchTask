package com.tmdb.movie.baseFragment;

public interface BaseView {

    void updateProgressBar(int visibility);

    void showSnackBarMessage(String message, boolean hasRetry);

}
