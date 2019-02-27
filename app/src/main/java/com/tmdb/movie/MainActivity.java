package com.tmdb.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tmdb.movie.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, MainFragment.newInstance(this)).commit();
    }
}
