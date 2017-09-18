package com.example.felixh.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Felix Huang on 9/16/2017.
 */

public class Movie implements Comparable<Movie> {
    String title;
    String overview;

    public double getRating() {
        return rating;
    }

    double rating;

    public int compareTo(Movie other) {
        if (this.rating < other.getRating()) return 1;
        else if (this.rating > other.getRating()) return -1;
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }
    int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    String posterUrl;
    String backdropUrl;
    public Movie(JSONObject jsonObj) {
        try {
            title = jsonObj.getString("title");
            overview = jsonObj.getString("overview");
            posterUrl = "https://image.tmdb.org/t/p/w342" + jsonObj.getString("poster_path");
            backdropUrl = "https://image.tmdb.org/t/p/w780" + jsonObj.getString("backdrop_path");
            rating = jsonObj.getDouble("vote_average");
            id = jsonObj.getInt("id");
            releaseDate = jsonObj.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Movie> parseJsonArray(JSONArray arr) {
        ArrayList<Movie> res = new ArrayList<>();
        PriorityQueue<Movie> p = new PriorityQueue<>();
        for (int k = 0; k < arr.length(); k++) {
            try {
                p.add(new Movie(arr.getJSONObject(k)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        while (p.size() > 0) {
            res.add(p.remove());
        }
        return res;
    }
}
