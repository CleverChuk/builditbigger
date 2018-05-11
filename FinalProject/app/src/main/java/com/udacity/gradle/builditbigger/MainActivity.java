package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.cleverchuk.showjokes.ShowJokesActivity;


public class MainActivity extends AppCompatActivity implements RequestJokeTask.OnRequestDone {
    private static final String JOKE = "joke";
    private RequestJokeTask mTask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTask = new RequestJokeTask(this);
        progressBar = findViewById(R.id.progress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        progressBar.setVisibility(View.VISIBLE);
        mTask.cancel(true);
        mTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTask.cancel(true);
        mTask = null;
    }

    @Override
    public void onComplete(String response) {
        Intent intent = new Intent(this, ShowJokesActivity.class);
        intent.putExtra(JOKE, response);
        startActivity(intent);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Log.e("Error", error);
        progressBar.setVisibility(View.GONE);
    }
}
