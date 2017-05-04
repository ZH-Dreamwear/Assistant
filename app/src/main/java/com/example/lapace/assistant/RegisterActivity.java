package com.example.lapace.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RegisterActivity";
    private EditText mUsername;
    private EditText mPassword;
    private Button mNext;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: ");
        viewInit();
    }

    //绑定控件，帐号输入框，密码输入框，确认按钮。
    void viewInit(){
        mUsername = (EditText)findViewById(R.id.edt_username);
        mPassword = (EditText)findViewById(R.id.edt_password);
        mNext = (Button)findViewById(R.id.btn_next);
        mNext.setOnClickListener(this);
        toolbar = (Toolbar)findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0x2);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next://返回调用界面
                Log.d(TAG, "onClick: R.id.btn_next");
                sendRequestWithHttpURLConnection(mUsername.getText().toString(),mPassword.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("Username",mUsername.getText().toString());
                intent.putExtra("Password",mPassword.getText().toString());

                setResult(0x1, intent);
                finish();
                break;
            default:
                Log.d(TAG, "onClick: Invalid operation");
                finish();
        }
    }

    private void sendRequestWithHttpURLConnection(final String username, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try{
                    String  s= "http://115.159.2.72/login/register.php?username="+ URLEncoder.encode(username,"utf-8")+
                                "&password="+URLEncoder.encode(password,"utf-8");
                    URL url = new URL(s);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder responseBuf = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        responseBuf.append(line);
                    }

                    //showResponse(parseXMLWithPull(responseBuf.toString()));
                    showResponse(responseBuf.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }

                finally{
                    if(reader != null){
                        try{
                            reader.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String responseBuf){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, responseBuf, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
