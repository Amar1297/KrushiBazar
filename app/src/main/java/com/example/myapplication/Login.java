package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button log_btn,reg_btn;
    EditText email,password;
    TextView forget_password;
    private ProgressDialog mProgress;
    private SharedPreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();
        log_btn=findViewById(R.id.login_btn);
        reg_btn=findViewById(R.id.reg_btn);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        forget_password=findViewById(R.id.forget_passward);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        preferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());

        if(preferenceManager.getsession()){
            Intent intent=new Intent(getApplicationContext(),DashBoard.class);
            startActivity(intent);
            finish();
        }

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Pass = password.getText().toString();

                if(Email.isEmpty()){
                    email.setError("Please Enter Email ID");
                } else if(Pass.isEmpty()){
                    password.setError("Please Enter Password");
                }else  {
                    mProgress.show();
                    Login();
                }

            }
        });

        forget_password = (TextView) findViewById(R.id.forget_passward);
        forget_password.setPaintFlags(forget_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    public void Login(){
        StringRequest request=new StringRequest(Request.Method.POST, "https://krushibazaarofficial1.000webhostapp.com/Login.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (response.contains("Login"))
                {


                    preferenceManager.setEmail(email.getText().toString().trim());
                    preferenceManager.setsession(true);
                    mProgress.dismiss();
                    Intent intent=new Intent(getApplicationContext(),DashBoard.class);
                    //intent.putExtra("email",email.getText().toString().trim());
                    startActivity(intent);
                    finish();

                }

                else {

                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Invalid UserName OR Password ",Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        finish();
    }
}
