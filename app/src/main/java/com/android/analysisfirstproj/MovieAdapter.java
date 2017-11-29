package com.android.analysisfirstproj;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehseti on 24.11.2017.
 */

public class MovieAdapter extends ArrayAdapter<Movies>
{
    private Context mContext;
    private List<Movies> moviesList = new ArrayList<>();

    public MovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movies> moviesList) {
        super(context, 0, moviesList);
        mContext = context;
        this.moviesList = moviesList;
    }

    public MovieAdapter(HomePageActivity homePageActivity, ArrayList<Movies> moviesList) {
        super(homePageActivity,0);
        mContext = homePageActivity;
        moviesList = moviesList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Movies currentMovie = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentMovie.getDrawableImage());

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentMovie.getMovieName());



        return listItem;
       // return super.getView(position, convertView, parent);
    }
}
