package com.tmdb.movie.Fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tmdb.movie.R;
import com.tmdb.movie.constant.Constants;
import com.tmdb.movie.datasource.model.Result;
import com.tmdb.movie.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.HomeViewHolder> {

    private List<Result> movieList = new ArrayList<>();

    private MainContract.Presenter mPresenter;

    public MainAdapter(MainContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Result result = movieList.get(position);

        if (!TextUtils.isEmpty(result.getTitle())) {
            holder.textViewTitle.setText(result.getTitle());
        }
        if (!TextUtils.isEmpty(result.getOverview())) {
            holder.textViewDescription.setText(result.getOverview());
        }
        if (!TextUtils.isEmpty(result.getOriginalLanguage())) {
            holder.textViewMovieLanguage.setText(result.getOriginalLanguage().toUpperCase());
        }
        if (!TextUtils.isEmpty(String.valueOf(result.getVoteAverage()))) {
            holder.textViewMovieRating.setText(String.valueOf(result.getVoteAverage()));
        }
        if (!TextUtils.isEmpty(result.getPosterPath())) {
            String imageUrl = Constants.UrlConstant.IMAGE_BASE_URL.concat(result.getPosterPath());
            Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imageViewMoviePoster);
        }
        if (!TextUtils.isEmpty(result.getReleaseDate())) {
            holder.textViewMovieDate.setText(DateUtils.convertDate(result.getReleaseDate()));
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewMovieTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewDescription)
        TextView textViewDescription;
        @BindView(R.id.textViewMovieRating)
        TextView textViewMovieRating;
        @BindView(R.id.textViewMovieLanguage)
        TextView textViewMovieLanguage;
        @BindView(R.id.textViewMovieDate)
        TextView textViewMovieDate;
        @BindView(R.id.imageViewDescriptionSeeMore)
        ImageView imageViewDescriptionSeeMore;
        @BindView(R.id.imageViewMoviePoster)
        ImageView imageViewMoviePoster;

        HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onItemClick(movieList.get(getAdapterPosition()).getId(),movieList.get(getAdapterPosition()).getTitle());
                }
            });
        }
    }

    public void addMovies(List<Result> results) {
        movieList.addAll(results);
        notifyDataSetChanged();
    }

}
