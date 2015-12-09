package com.count.andy.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by andy on 15-11-19.
 */
public class GetInputStream {
    public static InputStream getInputStream(String urls) throws IOException {
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
            return is;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return  is;
    }
}
