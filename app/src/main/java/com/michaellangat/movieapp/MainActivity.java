package com.michaellangat.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ion.with(this)
                .load("https://api.themoviedb.org/3/discover/movie?api_key=115ec20dbd8d761d14676f38f0858b23")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        try {
                            JSONObject json = new JSONObject(result);
                            JSONArray movies = json.getJSONArray("results");
                            for (int i = 0; i<movies.length(); i++) {
                                JSONObject movie = movies.getJSONObject(i);
                                String movie_title = movie.getString("original_title");
                                String movie_poster_url = movie.getString("poster_path");
                                loadImage(movie_title, movie_poster_url, movie);
                            }
                        } catch (JSONException jsone){
                            Log.d("Json", "Json error", jsone);
                        }
                    }
                });
        TextView searchBox = (TextView) findViewById(R.id.movie_search);
    }


    public void loadImage(String title, String url, JSONObject movie ) {
        CardView card = new CardView(this);
        ImageView movie_cover = new ImageView(this);
        TextView movie_title = new TextView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        int dp1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                this.getResources().getDisplayMetrics());
        CardView.LayoutParams params = new CardView.LayoutParams(
                dp1*170,
                dp1*270
        );
        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp1*220
        );
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp1*50
        );
        ViewGroup.LayoutParams params3 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.setMargins(5, 5, 5, 5);
        params.bottomMargin =5;
        params.topMargin = 10;
        //card.requestLayout();
        card.setLayoutParams(params);
        card.setRadius(10);
        card.setPadding(0,0,0,0);
        movie_cover.setLayoutParams(params1);
        movie_cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        movie_title.setLayoutParams(params2);
        movie_title.setTextSize(12);
        movie_title.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(params3);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(movie_cover);
        linearLayout.addView(movie_title);
        GridLayout grid = findViewById(R.id.grid_main);
        card.addView(linearLayout);
        grid.addView(card);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500"+url)
                .into(movie_cover);
        movie_title.setText(title);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetails.class);
                movieDetailsIntent.putExtra("movie_details", movie.toString());
                startActivity(movieDetailsIntent);
            }
        });
    }
}
