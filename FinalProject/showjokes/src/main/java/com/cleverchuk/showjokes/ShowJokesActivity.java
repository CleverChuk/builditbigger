package com.cleverchuk.showjokes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowJokesActivity extends AppCompatActivity {
    private static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showjokes);
        Intent intent = getIntent();
        String joke = intent == null ? "" : intent.getStringExtra(JOKE);
        TextView view = findViewById(R.id.jokes_tv);
        view.setText(joke);
    }
}
