package com.michaellangat.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //get movie details from main activity
        Intent movieIntent = getIntent();
        String movieDetailsString = movieIntent.getStringExtra("movie_details");

        //create movie JSONObj
        try {
            JSONObject movieObj = new JSONObject(movieDetailsString);
            Log.d("Movie_Details",movieObj.optString("movie"));
            populateView(movieObj);

        } catch(JSONException e){
            Log.d("MovieDetailsActivity", "Casting movie string to Json", e);
        }
    }
     protected void populateView(JSONObject movie) throws JSONException {
         TextView movieTitle = findViewById(R.id.movie_details_title);
         movieTitle.setText(movie.getString("title"));
     }
}
