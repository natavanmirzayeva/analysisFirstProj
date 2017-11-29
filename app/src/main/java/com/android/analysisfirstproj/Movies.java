package com.android.analysisfirstproj;

import java.io.Serializable;

/**
 * Created by mehseti on 24.11.2017.
 */

public class Movies implements Serializable
{
    private String movieName;
    private int movieId;
    private String movieType;
    private int drawableImage;
    private String movieContent;

    public Movies(){}
    public Movies(int after, String s, String movieType, String movieContent) {
        drawableImage = after;
        movieName  = s;
        this.movieType = movieType;
        this.movieContent = movieContent;
    }

    public String getMovieContent() {
        return movieContent;
    }

    public void setMovieContent(String movieContent) {
        this.movieContent = movieContent;
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
}
