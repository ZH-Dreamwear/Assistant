package com.example.lapace.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RegisterActivity";
    private EditText mUsername;
    private EditText mPassword;
    private Button mNext;
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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_next://返回调用界面
                Log.d(TAG, "onClick: R.id.btn_next");
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
}
