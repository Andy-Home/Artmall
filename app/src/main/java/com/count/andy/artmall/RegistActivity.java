package com.count.andy.artmall;

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
public class RegistActivity extends Activity implements View.OnClickListener {
    private Button check, regist;
    private EditText phoneNum, checkNum, password;
    private String phoneNumber, CheckNum, passWord;

    private static final String URL2 = "http://www.artmall.com/app/regist";
    private static final String URL1 = "http://www.artmall.com/app/sendSmsForgetPass";
    Boolean flag;
    private String status, id, biddingAmount, mobilPhone, nickname, error;
    private String send1, send2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        findViewById();
        onClickListener();
    }

    private void onClickListener() {
        check.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    private void findViewById() {
        check = (Button) findViewById(R.id.activity_regist_check_bg);
        regist = (Button) findViewById(R.id.activity_regist);
        phoneNum = (EditText) findViewById(R.id.activity_regist_phonenum);
        checkNum = (EditText) findViewById(R.id.activity_regist_check);
        password = (EditText) findViewById(R.id.activity_regist_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_regist_check_bg:
                flag = true;
                phoneNumber = String.valueOf(phoneNum.getText());
                send1 = "phoneOrEmail=" + phoneNumber;
                new downloaddata().execute();
                break;
            case R.id.activity_regist:
                flag = false;
                phoneNumber = String.valueOf(phoneNum.getText());
                CheckNum = String.valueOf(checkNum.getText());
                passWord = String.valueOf(password.getText());
                send2 = "phoneOrEmail=" + phoneNumber + "&password=" + passWord + "&validcode=" + CheckNum;
                new downloaddata().execute();
                break;
        }
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            String URL = "";
            String send = "";
            if (flag = true) {
                URL = URL1;
                send = send1;
            } else {
                URL = URL2;
                send = send2;
            }
            HttpUtil httpUtil = new HttpUtil(URL);
            String str = null;
            try {
                str = httpUtil.sendPost(send);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(" ", "Unable to retrieve web page. URL may be invalid.");
            }
            parseJson(str);
            if (flag = true) {
                Log.d("check", str);
            } else {
                Log.d("regist", str);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (status.equals("1")) {
                Intent intent = new Intent(RegistActivity.this, CenterActivity.class);
                startActivity(intent);
            } else if (!status.equals("3")) {
                Toast toast = new Toast(RegistActivity.this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                TextView textView = new TextView(RegistActivity.this);
                textView.setText(error);
                textView.setWidth(250);
                textView.setHeight(70);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(R.drawable.button_back);
                toast.setView(textView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                ;
            }
        }

        private void parseJson(String str) {
            try {
                status = new JSONObject(str).getString("Status");
                error = new JSONObject(str).getString("error");
                if (status.equals("2")) {
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

}
