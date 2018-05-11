package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;

/**
 * Created by chuk on 5/10/18,at 19:17.
 */

public class RequestJokeTask extends AsyncTask<Void,String,String> {
    private static JokeApi jokeApi;
    public interface OnRequestDone{
        void onComplete(String response);
        void onError(String error);
    }

    private OnRequestDone mListener;

    public RequestJokeTask(OnRequestDone listener){
        mListener = listener;
    }
    @Override
    protected String doInBackground(Void... voids) {
        if(jokeApi == null) jokeApi = buildApi();

        try{
            return jokeApi.tellJoke().execute().getData();
        }catch (IOException e){
            onProgressUpdate(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onComplete(s);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        mListener.onError(values[0]);
    }

    private JokeApi buildApi(){

        return new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),
                null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                })
                .build();
    }
}
