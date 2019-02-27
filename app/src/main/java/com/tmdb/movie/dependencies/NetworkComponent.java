package com.tmdb.movie.dependencies;

import com.tmdb.movie.datasource.repository.DataSource;

import dagger.Component;

@Component(modules = ClientModule.class)
public interface NetworkComponent {
    void inject(DataSource dataSource);
}
