package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static SharedPreferenceManager mInstance;
    private  static Context mCtx;
    private static final String SHARED_PREF_NAME="mysharedpref";
    private static final String KEY_USER_EMAIL="useremail";
    private static final String KEY_USER_PASSWORD="userpassword";

    private static final String KEY_USER_OWNERNAME="userownername";
    private static final String KEY_USER_MOBILE="usermobile";
    private static final String KEY_USER_ADDRESS="useraddress";
    private SharedPreferenceManager(Context context)
    {
        mCtx=context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context){
        if(mInstance == null)
        {
            mInstance=new SharedPreferenceManager(context);
        }
        return  mInstance;
    }

    public  boolean userRegister(String email,String password,String shopname,String ownername,String mobile,String address){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_PASSWORD,password);

        editor.putString(KEY_USER_OWNERNAME,ownername);
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_ADDRESS,address);
        editor.apply();
        return  true;
    }

    public  boolean isRegisteresIn()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL,null)!= null)
        {
            return  true;
        }
        return  false;
    }

    public  boolean logout()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return  true;

    }

    public String getEmail()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }

    public String getPassword()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PASSWORD,null);
    }

    public String getOwnername()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_OWNERNAME,null);
    }



    public String getMobile()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MOBILE,null);
    }

    public String getAddress()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ADDRESS,null);
    }
}
