package com.example.indee;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ProgressBar progressBar;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    final int cacheSize = maxMemory / 8;
    LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };
    public DownloadImageTask(ImageView bmImage, ProgressBar progress) {
        this.bmImage = bmImage;
        this.progressBar = progress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        if(getBitmapFromMemCache(urldisplay)!=null){
            return getBitmapFromMemCache(urldisplay);
        }
        else {
            Bitmap bitmap = null,decoded=null;
            try {
                URL url = new URL(urldisplay);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                addBitmapToMemoryCache(urldisplay,decoded);
            } catch (IOException e) {
                e.printStackTrace();
            }
           return decoded;
        }

    }

    protected void onPostExecute(Bitmap result) {
        progressBar.setVisibility(View.GONE);
        bmImage.setImageBitmap(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }


}
