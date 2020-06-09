package com.michaellangat.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //get movie details from main activity
        Intent movieIntent = getIntent();
        String movieDetailsString = movieIntent.getStringExtra("movie");
    }
}
