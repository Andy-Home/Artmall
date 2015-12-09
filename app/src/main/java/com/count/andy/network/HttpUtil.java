package com.count.andy.network;

import android.util.Log;

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

/**
 * Created by andy on 15-11-11.
 */
public class HttpUtil {
    private String url;

    public HttpUtil(String url) {
        this.url = url;
    }

    public String downloaddata() throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000	/*	milliseconds	*/);
            conn.setConnectTimeout(15000	/*	milliseconds	*/);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //	Starts	the	query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("response", "The	response	is:	" + response);
            is = conn.getInputStream();
            //	Convert	the	InputStream	into	a	string
            String contentAsString = readIt(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String sendPost(String params) throws IOException {
        InputStream in = null;
        try {
            URL realurl = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) realurl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(params);
            pw.flush();
            pw.close();
            in = conn.getInputStream();
            String contentAsString = readIt(in);
            return contentAsString;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    private String readIt(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
