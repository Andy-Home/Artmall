package com.count.andy.artmall;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.count.andy.network.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by andy on 15-12-2.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String URL = "http://www.artmall.com/app/login";
    private Button login, regist;
    private EditText name, num;
    private String username, password, send;
    private String status, id, biddingAmount, mobilPhone, nickname, error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        findViewById();
        onClickListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        name.setText("");
        num.setText("");
    }

    private void onClickListener() {
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    private void findViewById() {
        login = (Button) findViewById(R.id.login_bg);
        regist = (Button) findViewById(R.id.login_regist);
        name = (EditText) findViewById(R.id.login_name);
        num = (EditText) findViewById(R.id.login_num);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bg:
                username = String.valueOf(name.getText());
                password = String.valueOf(num.getText());
                send = "phoneOrEmail=" + username + "&password=" + password;
                new downloaddata().execute();
                break;
            case R.id.login_regist:
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            HttpUtil httpUtil = new HttpUtil(URL);
            String str = null;
            try {
                str = httpUtil.sendPost(send);
                Log.d("login", str);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(" ", "Unable to retrieve web page. URL may be invalid.");
            }
            parseJson(str);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (status.equals("2")) {
                Intent intent = new Intent(LoginActivity.this, CenterActivity.class);
                startActivity(intent);
            } else {
                Toast toast = new Toast(LoginActivity.this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                TextView textView = new TextView(LoginActivity.this);
                textView.setText(error);
                textView.setWidth(250);
                textView.setHeight(70);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(R.drawable.button_back);
                toast.setView(textView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    private void parseJson(String str) {
        try {
            status = new JSONObject(str).getString("Status");
            error = new JSONObject(str).getString("error");
            if(status.equals("2")){
                JSONArray jsonObjs = new JSONObject(str).getJSONArray("data");
                for (int i = 0; i < jsonObjs.length(); i++) {
                    JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                    id = jsonObj.getString("id");
                    biddingAmount = jsonObj.getString("biddingAmount");
                    mobilPhone = jsonObj.getString("mobilPhone");
                    nickname = jsonObj.getString("nickname");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
