package com.example.android.yugiohcardmarket.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by termitavee on 02/03/17.
 */

public class ImageGetter extends AsyncTask<String, Integer, Drawable> {
    Context context;
    public ImageGetter(Context context){
        this.context = context;
    }
    protected Drawable doInBackground(String... urls) {
        if (urls[0] == null)
            return null;
        Drawable d;
        try {
            String url = "https://en.yugiohcardmarket.eu" + urls[0];
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-agent", "Mozilla/4.0");

            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap x = BitmapFactory.decodeStream(input);
            Log.i("LoadImageFromWeb ", "Bitmap=" + x);
            d = new BitmapDrawable(context.getResources(), x);
        }catch(Exception e){
            return null;
        }
        return d;
    }
}
