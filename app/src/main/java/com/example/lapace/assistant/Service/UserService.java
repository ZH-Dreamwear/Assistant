package com.example.lapace.assistant.Service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class UserService extends Service {

    private static final String TAG = "UserService";
    private  UserBinder mBinder=new UserBinder();
    public UserService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    public class UserBinder extends Binder {

        private static final String TAG = "UserBinder";

        //用户信息
        private String mUsername;
        private String mNickname;
        private String mSex;
        private Bitmap mDisplayPhoto;
        private String mInvidualSiguature;
        private  String mArea;

        private int mResult=-1;
        public Thread t;

        //登录:requestMode=1，requestURL="lapace/login/login.php"； 注册：requestMode=2,requestURL="lapace/login/register.php"
        public Thread sendRequestWithHttpURLConnection(final String requestURL, final Map<String,Object> map, final int requestMode){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String requestURL_s = "http://115.159.2.72/"+requestURL;
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    StringBuilder responseBuf = new StringBuilder();
                    try{
                        URL url = new URL(requestURL_s);
                        connection = (HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setRequestProperty("Charset","UTF-8");
                        if(requestMode==1){
                            String username = (String) map.get("username");
                            String password = (String) map.get("password");
                            String requestString = "username="+username+"&password="+password;
                            OutputStream os = connection.getOutputStream();
                            os.write(requestString.getBytes());
                            os.flush();
                            os.close();
                        }
                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while((line=reader.readLine())!=null){
                            responseBuf.append(line);
                        }

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
                    String s = responseBuf.toString();
                    s=s.substring(2);
                    Log.d(TAG, "run:a "+s);
                    String[] temp = s.trim().split("_");
                    int i=0;
                    switch(temp.length){
                        case 1:
                            Integer.getInteger(temp[0]);
                            break;
                        case 7:
                            Log.d(TAG, "run: 7");
                            Log.d(TAG, "run: "+temp[0].length()+' '+temp[0]);
                            if(temp[0].equals("0")){
                                mResult=0;
                                Log.d(TAG, "run: "+Integer.toString(mResult));
                            }else if(temp[0].equals("1")){
                                mResult=1;
                                Log.d(TAG, "run: "+Integer.toString(mResult));
                            }else if(temp[0].equals("2")){
                                mResult=2;
                                Log.d(TAG, "run: "+Integer.toString(mResult));
                            }
                            mUsername=temp[1];
                            mNickname=temp[2];
                            if(temp[3].equals("1")){
                                mSex="男";
                            }else if(temp[3].equals("0")){
                                mSex="女";
                            }else{
                                mSex="";
                            }
                            downloadDisplayPhoto(temp[4]);
                            mInvidualSiguature=temp[5];
                            mArea=temp[6];
                            Log.d(TAG, "run: z");
                            break;
                    }
                    Log.d(TAG, "run: mUserName "+mUsername);
                    Log.d(TAG, "run: mUserName "+mNickname);
                    Log.d(TAG, "run: "+this.getClass().getName()+' '+this.toString());
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thread;
        }

        private void downloadDisplayPhoto(final String s) {
                    String requestURL_s = "http://115.159.2.72/" + s;
                    HttpURLConnection connection = null;
                    InputStream in;
                    try {
                        URL url = new URL(requestURL_s);
                        connection =(HttpURLConnection)url.openConnection();
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        InputStream ina = connection.getInputStream();
                        mDisplayPhoto= BitmapFactory.decodeStream(ina);
                        ina.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        }

        public String getUsername(){
            Log.d(TAG, "getUsername: "+mUsername);
            return mUsername;
        }
        public  String getNickname(){
            return mNickname;
        }
        public String getSex(){
            return mSex;
        }
        public Bitmap getDisplayPhoto(){
            return mDisplayPhoto;
        }
        public String getInvidualSiguature(){
            return mInvidualSiguature;
        }
        public String getArea(){
            return mArea;
        }
        public int getResult(){

            return  mResult;
        }
    }
}
