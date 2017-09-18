package com.example.felixh.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.felixh.flickster.R;
import com.example.felixh.flickster.models.Movie;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by Felix Huang on 9/16/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    Picasso picasso;
    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        // Use OkHttpClient singleton
        OkHttpClient client = new OkHttpClient();
        picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get data item for the position
        Movie movie = getItem(position);

        class ViewHolder {
            public ImageView ivPoster;
            public TextView tvTitle;
            public TextView tvOverview;
        }

        ViewHolder holder;
        // check if we're using a recycled view, if not we need to inflate
        if (convertView == null) { // need to create a new view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            // parent : current container; false : not to attach this item view to container yet
            holder = new ViewHolder();
            holder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // look up views for populating the data (image, caption)
        ImageView ivPoster = holder.ivPoster;
        TextView tvTitle = holder.tvTitle;
        TextView tvOverview = holder.tvOverview;

        // insert the model data into each item views
        // clear image view
        ivPoster.setImageResource(0);

        // insert the image using picasso; send asynch out which runs in background
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (movie.getPosterUrl() != null) { // to do : to check more for valid url
                picasso.with(getContext()).load(movie.getPosterUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(ivPoster);
            }
        } else if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (movie.getBackdropUrl() != null) { // to do : to check more for valid url
                picasso.with(getContext()).load(movie.getBackdropUrl())
                        .placeholder(R.mipmap.ic_launcher).into(ivPoster);
            }
        }

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview() + "\nRating : " + movie.getRating());
        return convertView;
    }
}
