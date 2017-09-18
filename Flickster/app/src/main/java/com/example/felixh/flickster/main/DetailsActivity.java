package com.example.felixh.flickster.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.felixh.flickster.R;
import com.example.felixh.flickster.data.CurrMovie;
import com.example.felixh.flickster.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        Movie movie = CurrMovie.getCurrentMovie();
        setTitle(movie.getTitle());

        ImageView ivPosterDt = (ImageView) findViewById(R.id.ivPosterDt);
        // insert the image using picasso; send asynch out which runs in background
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (movie.getPosterUrl() != null) { // to do : to check more for valid url
                Picasso.with(this).load(movie.getPosterUrl()).into(ivPosterDt);
            }
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (movie.getBackdropUrl() != null) { // to do : to check more for valid url
                Picasso.with(this).load(movie.getBackdropUrl()).into(ivPosterDt);
            }
        }

        TextView tvTitleDt = (TextView) findViewById(R.id.tvTitleDt);
        tvTitleDt.setText(movie.getTitle());

        TextView tvOverviewDt = (TextView) findViewById(R.id.tvOverviewDt);
        tvOverviewDt.setText(movie.getOverview() + "\nRating : " + movie.getRating());

        TextView tvRelDateDt = (TextView) findViewById(R.id.tvRelDateDt);
        tvRelDateDt.setText("Release Date : " + movie.getReleaseDate());

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

}
