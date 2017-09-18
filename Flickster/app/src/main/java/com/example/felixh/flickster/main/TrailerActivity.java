package com.example.felixh.flickster.main;

import android.os.Bundle;

import com.example.felixh.flickster.R;
import com.example.felixh.flickster.data.CurrMovie;
import com.example.felixh.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        final YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        Movie movie = CurrMovie.getCurrentMovie();
        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray videoJsonArray = response.getJSONArray("results");
                    for (int k = 0; k < videoJsonArray.length(); k++) {
                        try {
                            JSONObject videoObj = videoJsonArray.getJSONObject(k);
                            String name = videoObj.getString("name");
                            if (name.contains("Trailer")) {
                                final String key = videoObj.getString("key");
                                youTubePlayerView.initialize("a07e22bc18f5cb106bfe4cc1f83ad8ed",
                                        new YouTubePlayer.OnInitializedListener() {
                                            @Override
                                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                                YouTubePlayer youTubePlayer, boolean b) {

                                                // do any work here to cue video, play video, etc.
                                                youTubePlayer.cueVideo(key);
                                            }
                                            @Override
                                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                                YouTubeInitializationResult youTubeInitializationResult) {

                                            }
                                        });
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

}
