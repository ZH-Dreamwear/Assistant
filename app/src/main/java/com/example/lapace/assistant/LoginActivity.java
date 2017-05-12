package com.example.lapace.assistant;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lapace.assistant.Service.UserService;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    //帐号，
    private EditText mUsername;
    //密码
    private EditText mPassword;
    //登录按钮
    private Button mLogin;
    //注册按钮
    private Button mRegister;

    //测试时候使用，忽略登录跳转到主界面。
    private Button goMain;

    private Intent userServiceIntent;
    private UserService.UserBinder userBinder;
    private ServiceConnection userServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            userBinder = (UserService.UserBinder) service;
            Log.d(TAG, "onServiceConnected: "+userBinder.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userServiceIntent = new Intent(this, UserService.class);
        startService(userServiceIntent);
        bindService(userServiceIntent, userServiceConnection, BIND_AUTO_CREATE);
        //startService(userServiceIntent);
        viewInit();


    }

    //控件初始化
    void viewInit(){
        mUsername = (EditText)findViewById(R.id.edt_username);
        mPassword = (EditText)findViewById(R.id.edt_password);
        mLogin = (Button)findViewById(R.id.btn_login);
        mRegister = (Button)findViewById(R.id.btn_register);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        //测试时使用，直达主界面及其监听器。
        goMain = (Button)findViewById(R.id.btn_Main);
        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        //stopService(userServiceIntent);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_login://登录按钮，成功跳转到主界面，失败输出提示信息，未完成。
                //bindService(userServiceIntent, userServiceConnection, BIND_AUTO_CREATE);
                Map<String, Object> map= new HashMap<String,Object>();
                map.put("username",mUsername.getText().toString());
                map.put("password",mPassword.getText().toString());
                Thread thread=userBinder.sendRequestWithHttpURLConnection("/lapace/login/login.php", map, 1);
                try {
                    Log.d(TAG, "onClick: join start"+Integer.toString(userBinder.getResult()));
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onClick: username "+userBinder.getUsername());
                if(userBinder.getResult()==0){
                    Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                }else if(userBinder.getResult()==1){
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }else if(userBinder.getResult()==2){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    unbindService(userServiceConnection);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                }
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
        }
    }

}
