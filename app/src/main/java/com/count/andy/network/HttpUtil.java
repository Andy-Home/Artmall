package com.count.andy.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 15-11-11.
 */
public class HttpUtil {
    private String url;
    private String response;
    RequestQueue mQueue;

    public HttpUtil(String url, Context context) {
        this.url = url;
        mQueue = Volley.newRequestQueue(context);
    }

    public void downloaddata() throws IOException {
        Request stringRequest = new StringRequest(this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                        Return(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        stringRequest.setShouldCache(true);

        mQueue.add(stringRequest);
    }

    private void Return(String response) {
        this.response = response;
    }

    public String sendPost(final String params) throws IOException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                        Return(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", params);
                return map;
            }
        };
        mQueue.add(stringRequest);
        return response;
    }

    public String getString() {
        return this.response;
    }
}
