package com.tmdb.movie.dependencies;

import com.tmdb.movie.constant.Constants;
import com.tmdb.movie.datasource.repository.TmdbClient;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ClientModule {

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(Constants.UrlConstant.BASE_URL)
                .build();
    }

    @Provides
    public TmdbClient tmdbClient(Retrofit retrofit) {
        return retrofit.create(TmdbClient.class);
    }
}
