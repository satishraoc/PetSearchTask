package com.tmdb.movie.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tmdb.movie.MainActivity;
import com.tmdb.movie.R;
import com.tmdb.movie.baseFragment.BaseFragment;
import com.tmdb.movie.constant.Constants;
import com.tmdb.movie.datasource.model.Genre;
import com.tmdb.movie.datasource.model.MovieDetail;
import com.tmdb.movie.datasource.repository.DataSource;
import com.tmdb.movie.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullDetailFragment extends BaseFragment<FullDetailPresenter> implements FullDetailContract.View {

    @BindView(R.id.textViewDetailDescription)
    TextView textViewDetailDescription;
    @BindView(R.id.textViewDetailDuration)
    TextView textViewDetailDuration;
    @BindView(R.id.textViewDetailLanguage)
    TextView textViewDetailLanguage;
    @BindView(R.id.textViewDetailRating)
    TextView textViewDetailRating;
    @BindView(R.id.imageViewDetailPoster)
    ImageView imageViewDetailPoster;
    @BindView(R.id.textViewDetailReleaseDate)
    TextView textViewDetailReleaseDate;
    @BindView(R.id.textViewDetailGenre)
    TextView textViewDetailGenre;

    @BindView(R.id.textViewDetailRevenue)
    TextView textViewDetailRevenue;

    @BindView(R.id.textViewDetailBudget)
    TextView textViewDetailBudget;

    @BindView(R.id.progressBarMovieDetail)
    ProgressBar progressBarMovieDetail;

    @BindView(R.id.groupDetailMovie)
    Group groupMovieDetails;

    @BindView(R.id.coordinatorLayoutDetailMovie)
    CoordinatorLayout coordinatorLayoutDetailMovie;

    private Snackbar mSnackbar;
    private Context mContext;

    public static FullDetailFragment newInstance(int movieId, Context context, String movieName) {
        Bundle args = new Bundle();
        args.putInt(Constants.MovieBundle.MOVIE_ID, movieId);
        args.putString(Constants.MovieBundle.MOVIE_NAME, movieName);
        FullDetailFragment fragment = new FullDetailFragment();
        fragment.mContext = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        String toolbarName = null;
        if (getArguments() != null) {
            toolbarName = getArguments().getString(Constants.MovieBundle.MOVIE_NAME);
        }
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(toolbarName);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public FullDetailPresenter createPresenter() {
        int id = 0;
        if (getArguments() != null) {
            id = getArguments().getInt(Constants.MovieBundle.MOVIE_ID);
        }
        return new FullDetailPresenter(this, new DataSource(), mContext, id);
    }

    @Override
    public void updateProgressBar(int visibility) {
        progressBarMovieDetail.setVisibility(visibility);
    }

    @Override
    public void showSnackBarMessage(String message, boolean hasRetry) {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
        int snackBarLength = hasRetry ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_SHORT;
        mSnackbar = Snackbar.make(coordinatorLayoutDetailMovie, message, snackBarLength);
        if (hasRetry) {
            mSnackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSnackbar.dismiss();
                    mPresenter.onSnackbarRetry();
                }
            });
        }
        mSnackbar.show();
    }

    @Override
    public void setMovieDetails(MovieDetail movieDetails) {
        textViewDetailDescription.setText(movieDetails.getOverview());
        textViewDetailDuration.setText(String.valueOf(movieDetails.getRuntime()).concat(" minutes"));
        textViewDetailLanguage.setText(movieDetails.getOriginalLanguage().toUpperCase());
        textViewDetailRating.setText(String.valueOf(movieDetails.getVoteAverage()));
        textViewDetailReleaseDate.setText(DateUtils.convertDate(movieDetails.getReleaseDate()));
        textViewDetailRevenue.setText(String.valueOf("$").concat(truncateNumber(movieDetails.getRevenue())));
        textViewDetailBudget.setText(String.valueOf("$").concat(truncateNumber(movieDetails.getBudget())));
        textViewDetailGenre.setText(getGenreName(movieDetails.genres));
        String imageUrl = Constants.UrlConstant.IMAGE_BASE_URL.concat(movieDetails.getBackdropPath());
        Glide.with(mContext).load(imageUrl).into(imageViewDetailPoster);

    }

    private String getGenreName(List<Genre> genreList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : genreList) {
            stringBuilder.append(genre.getName().concat(","));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public String truncateNumber(float floatNumber) {
        long million = 1000000L;
        long billion = 1000000000L;
        long number = Math.round(floatNumber);
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return Float.toString(fraction) + "Million";
        } else if (number >= billion) {
            float fraction = calculateFraction(number, billion);
            return Float.toString(fraction) + "Billion";
        }
        return Long.toString(number);
    }

    public float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        return (float) truncate * 0.10F;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
    }
}
