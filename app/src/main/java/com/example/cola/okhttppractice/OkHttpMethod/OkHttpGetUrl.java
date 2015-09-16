package com.example.cola.okhttppractice.OkHttpMethod;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by cola on 15/9/15.
 */
public class OkHttpGetUrl extends AsyncTask<String, Void, String> {

    OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);

        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
        }
        return null;
    }
}
