package com.example.cola.okhttppractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cola.okhttppractice.OkHttpMethod.OkHttpGetImg;
import com.example.cola.okhttppractice.OkHttpMethod.OkHttpGetUrl;
import com.example.cola.okhttppractice.OkHttpMethod.OkHttpPost;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText typeUrlEdtxt, typeImgUrlEdtxt, typePostPlay1Edtxt, typePostPlay2Edtxt;
    private TextView urlContent, postContent;
    private Button getUrlBtn, getImageBtn, postServerBtn;
    private ScrollView urlContentScroller;
    private FrameLayout imagePart;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    private void findView() {
        typeUrlEdtxt = (EditText)findViewById(R.id.type_url_edtxt);
        typeImgUrlEdtxt = (EditText)findViewById(R.id.type_img_url_edtxt);
        typePostPlay1Edtxt = (EditText)findViewById(R.id.type_post_play1_edtxt);
        typePostPlay2Edtxt = (EditText)findViewById(R.id.type_post_play2_edtxt);
        urlContent = (TextView)findViewById(R.id.url_content_txt);
        postContent = (TextView)findViewById(R.id.post_content_txt);
        getUrlBtn = (Button)findViewById(R.id.get_url_btn);
        getImageBtn = (Button)findViewById(R.id.get_image_btn);
        postServerBtn = (Button)findViewById(R.id.post_server_btn);

        urlContentScroller = (ScrollView)findViewById(R.id.url_content_scroller);

        imagePart = (FrameLayout)findViewById(R.id.img_part);

        mImage = (ImageView)findViewById(R.id.url_image);
    }

    private void setListener() {
        getUrlBtn.setOnClickListener(this);
        getImageBtn.setOnClickListener(this);
        postServerBtn.setOnClickListener(this);
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
//        urlContent.setText(Html.fromHtml(result));
        urlContent.append(result + "\n");
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_url_btn:
                downloadUrl();
                imagePart.setVisibility(View.GONE);
                urlContentScroller.setVisibility(View.VISIBLE);
                postContent.setVisibility(View.GONE);
                break;
            case R.id.get_image_btn:
                downloadImg();
                imagePart.setVisibility(View.VISIBLE);
                urlContentScroller.setVisibility(View.GONE);
                postContent.setVisibility(View.GONE);
                break;
            case R.id.post_server_btn:
                postToServer();
                imagePart.setVisibility(View.GONE);
                urlContentScroller.setVisibility(View.GONE);
                postContent.setVisibility(View.VISIBLE);
                break;
        }

    }
}
