package com.example.cola.okhttppractice.OkHttpMethod;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by cola on 15/9/16.
 */
public class OkHttpPost extends AsyncTask<String, Void, String> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {

        RequestBody body = RequestBody.create(JSON, params[0]);
        Request request = new Request.Builder()
                .url(params[1])
                .post(body)
                .build();

        try {

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
        }
        return null;
    }

    public String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,10],'color':-13388315,'total':41},"
                + "{'name':'" + player2 + "','history':[7,10,6,10,10],'color':-48060,'total':43}"
                + "]}";
    }

}
