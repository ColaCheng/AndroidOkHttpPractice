# AndroidOkHttpPractice
I've written the three examples that use OkHttp library to connect the http request.

1. Get An URL
2. Get An Image
3. Post To Server

##Setup the Environment

#### Add Library

To implement OkHttp library, make sure to add the Support Library setup instructions first. (make sure these versions have been updated.)

```gradle
dependencies {
  compile 'com.squareup.okhttp:mockwebserver:2.5.0'
}
```

#### Add Permission

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

##Get An URL

####MainActivity.java and add following code :

```java
public void downloadUrl() {

    String url = "http://" + typeUrlEdtxt.getText().toString();
    OkHttpGetUrl handler = new OkHttpGetUrl();
    String result = null;
    try {
        result = handler.execute(url).get();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (ExecutionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    urlContent.append(result + "\n");
}
```

####Create new class OkHttpGetUrl.java and add following code :

```java
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
```
 ![show1](http://i.imgur.com/Hz7XibE.png?1)

##Get An Image

####MainActivity.java and add following code :

```java
public void downloadImg() {
    mImage.setVisibility(View.VISIBLE);
    String url = "http://" + typeImgUrlEdtxt.getText().toString();
    OkHttpGetImg handler = new OkHttpGetImg();
    byte[] image = new byte[0];

    try {
        image = handler.execute(url).get();
        if (image != null && image.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
                    image.length);
            mImage.setImageBitmap(bitmap);
            mImage.setVisibility(View.VISIBLE);
        }
    } catch (Exception e) {
    }
}
```

####Create new class OkHttpGetImg.java and add following code :

```java
public class OkHttpGetImg extends AsyncTask<String, Void, byte[]> {

    OkHttpClient client = new OkHttpClient();

    @Override
    protected byte[] doInBackground(String... params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);

        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (Exception e) {
        }
        return null;
    }
}
```
 ![show2](http://i.imgur.com/aenfxuE.png?1)

##Post To Server

####MainActivity.java and add following code :

```java
public void postToServer() {
    String url = "http://www.roundsapp.com/post";
    postContent.setVisibility(View.VISIBLE);
    OkHttpPost handler = new OkHttpPost();
    String json = handler.bowlingJson(typePostPlay1Edtxt.getText().toString(), typePostPlay2Edtxt.getText().toString());
    String result = null;

    try {
        result = handler.execute(json, url).get();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (ExecutionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    postContent.setAutoLinkMask(Linkify.EMAIL_ADDRESSES| Linkify.WEB_URLS);
    postContent.setText(result);
}
```

####Create new class OkHttpPost.java and add following code :

```java
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
```
 ![show3](http://i.imgur.com/jZy3lVD.png?1)

## References

* <https://github.com/square/okhttp>
* <https://github.com/square/okhttp/wiki/Recipes>
* <http://www.skholingua.com/android-basic/other-sdk-n-libs/okhttp>
