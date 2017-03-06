package com.example.android.yugiohcardmarket.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by termitavee on 02/03/17.
 */

public class ImageGetter extends AsyncTask<String, Integer, Bitmap> {
    ImageView parent = null;

    public ImageGetter(ImageView parent) {
        this.parent = parent;
    }

    protected Bitmap doInBackground(String... urls) {
        if (urls[0] == null)
            return null;
        Bitmap bm;
        try {
            String url = "https://en.yugiohcardmarket.eu" + urls[0].replaceFirst(".", "");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-agent", "Mozilla/4.0");
            //Log.i("ImageGetter", "doInBackground url=" + url);
            connection.connect();
            InputStream input = connection.getInputStream();

            bm = BitmapFactory.decodeStream(input);


        } catch (Exception e) {
            Log.e("ImageGetter", "Exception e=" + e.getMessage());
            return null;

        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap b) {
        super.onPostExecute(b);
        parent.setImageBitmap(null);
        parent.setBackground(null);
        //parent.setImageResource(android.R.color.transparent);
        parent.setImageBitmap(b);
    }
}
