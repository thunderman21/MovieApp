package com.michaellangat.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
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
                                loadImage(movie_title, movie_poster_url);
                            }
                        } catch (JSONException jsone){
                            Log.d("Json", "Json error", jsone);
                        }
                    }
                });
    }

    public void loadImage(String title, String url) {
        CardView card = new CardView(this);
        ImageView movie_cover = new ImageView(this);
        TextView movie_title = new TextView(this);
        int dp1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                this.getResources().getDisplayMetrics());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                dp1*400,
                dp1*400
        );
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        card.setLayoutParams(params2);
        movie_cover.setLayoutParams(params2);
        movie_title.setLayoutParams(params2);
        GridLayout grid = findViewById(R.id.grid_main);
        ViewGroup views = (ViewGroup) grid;
        //card.addView(movie_cover);
        //card.addView(movie_title);
        grid.addView(movie_cover);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500"+url)
                .into(movie_cover);
        //movie_title.setText(title);
    }
}
