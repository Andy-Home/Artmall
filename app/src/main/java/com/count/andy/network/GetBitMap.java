package com.count.andy.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andy on 15-11-11.
 */
public class GetBitMap {
    Bitmap response;

    public GetBitMap() {
    }

    public void getBitmap(String urls, Context context) throws IOException {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        Request imageRequest = new ImageRequest(
                urls,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Return(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //imageView.setImageResource(R.drawable.default_image);
            }
        });
        imageRequest.setShouldCache(true);

        mQueue.add(imageRequest);
    }

    private void Return(Bitmap response) {
        this.response = response;
    }

    public Bitmap getpicture() {
        return response;
    }
}
