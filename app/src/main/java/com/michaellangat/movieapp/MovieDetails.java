package com.michaellangat.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Movie Details");

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
         TextView movieVotes = findViewById(R.id.votes);
         TextView movieTime = findViewById(R.id.time);
         TextView movieDescription = findViewById(R.id.description);
         ImageView moviePoster = findViewById(R.id.movie_details_poster);
         //TextView movieTitle = findViewById(R.id.movie_details_title);
         movieTitle.setText(movie.getString("title"));
         movieVotes.setText("Vote Average: "+movie.getString("vote_average"));
         movieTime.setText("Release Date: "+movie.getString("release_date"));
         movieDescription.setText(movie.getString("overview"));
         Picasso.get()
                 .load("https://image.tmdb.org/t/p/w500/"+movie.getString("poster_path"))
                 .into(moviePoster);
     }
}
