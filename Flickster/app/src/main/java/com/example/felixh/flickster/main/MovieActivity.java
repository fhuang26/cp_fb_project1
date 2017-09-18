package com.example.felixh.flickster.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.felixh.flickster.R;
import com.example.felixh.flickster.adapters.MovieAdapter;
import com.example.felixh.flickster.data.CurrMovie;
import com.example.felixh.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    ListView lvItems;
    int firstVisiblePos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);
        firstVisiblePos = 0;
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)lvItems.getAdapter().getItem(position);
                trailerOrDetailsNextDialog(MovieActivity.this, movie);
                // Item item = (Item)listview.getAdapter().getItem(position);
                // Intent intent = new Intent(context, NewsItemActivity.class);
                // context.startActivity(intent);
            }
        });
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");
                    movies.addAll(Movie.parseJsonArray(movieJsonArray));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    /*
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        firstVisiblePos = lvItems.getFirstVisiblePosition();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        lvItems.setSelection(firstVisiblePos);
    }
    */

    public boolean trailerOrDetailsNextDialog (Context cnt, Movie movie)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(cnt);
        ad.setTitle(movie.getTitle());
        CurrMovie.setCurrentMovie(movie);

        ad.setMessage(R.string.AlertDialogMsg);

        ad.setPositiveButton(
                "Details",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent(MovieActivity.this, DetailsActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ad.setNegativeButton(
                "Trailer",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent(MovieActivity.this, TrailerActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ad.show();
        return false;
    }
}
