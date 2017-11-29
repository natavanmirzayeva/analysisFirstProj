package com.android.analysisfirstproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Text extends AppCompatActivity {

    Intent intent;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        intent = getIntent();
        Movies movies = (Movies) intent.getSerializableExtra("name");
        txt = (TextView) findViewById(R.id.txt);
        txt.setText(movies.getMovieContent());
    }
}
