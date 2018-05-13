/*
 * MIT License
 *
 * Copyright (c) 2018 Chukwubuikem Ume-Ugwa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
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

    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource("Idler");

    public CountingIdlingResource getCountingIdlingResource() {
        return countingIdlingResource;
    }

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
        countingIdlingResource.increment();

        progressBar.setVisibility(View.VISIBLE);
        mTask.cancel(true);
        mTask = null;
        mTask = new RequestJokeTask(this);
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
        countingIdlingResource.decrement();
    }

    @Override
    public void onError(String error) {
        Log.e("Error", error);
        progressBar.setVisibility(View.GONE);
        countingIdlingResource.decrement();
    }
}
