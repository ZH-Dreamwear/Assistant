package com.example.lapace.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    //帐号，
    private EditText mUsername;

    //密码
    private EditText mPassword;

    //登录按钮
    private Button mLogin;

    //注册按钮
    private Button mRegister;//aaaaa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewInit();
        Log.d(TAG, "on create; "+" good");
    }

    //控件初始化,主要是绑定四个控件：帐号输入框，密码输入框，登录按钮，注册按钮。
    void viewInit(){
        mUsername = (EditText)findViewById(R.id.edt_username);
        mPassword = (EditText)findViewById(R.id.edt_password);
        mLogin = (Button)findViewById(R.id.btn_login);
        mRegister = (Button)findViewById(R.id.btn_register);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_login:
                Toast.makeText(LoginActivity.this, "login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_register://跳转到注册界面，RegisterActivity
                Toast.makeText(LoginActivity.this, "register", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.example.lapace.assistant.REGISTER");
                //requestCode = 0x1表示登录界面打开注册界面
                startActivityForResult(intent, 0x1);
                break;
            default:
                Log.d(TAG, "onClick: Invalid operation");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+Integer.toString(requestCode)+Integer.toString(resultCode));
        //requestCode=0x1且resultCode=0x1表示注册成功，更新编辑框的内容。
        if(requestCode == 0x1 & resultCode == 0x1){
            Bundle result = data.getExtras();
            mUsername.setText(result.getString("Username"));
            mPassword.setText(result.getString("Password"));
            Log.d(TAG, "onActivityResult: ");
            Log.d(TAG, "onActivityResult: ");
        }
    }
}
