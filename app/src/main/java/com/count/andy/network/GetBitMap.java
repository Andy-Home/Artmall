package com.count.andy.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andy on 15-11-11.
 */
public class GetBitMap {
    public static Bitmap getBitmap(String urls) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000	/*	milliseconds	*/);
            conn.setConnectTimeout(15000	/*	milliseconds	*/);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //	Starts	the	query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("response", "The response is:	" + response);
            is = conn.getInputStream();
            //	Convert	the	InputStream	into	a	string
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
