package com.android.analysisfirstproj;

/**
 * Created by mehseti on 25.11.2017.
 */

public class UserMovieRelationship
{
    private String email;
    private String movieType;
    private  String datee;

    public  UserMovieRelationship(){};
    public UserMovieRelationship(String email, String movieType,String date)
    {
        this.email = email;
        this.movieType = movieType;
        this.datee = date;
    }

    public String getDatee() {
        return datee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
}
