package com.example.myapplication;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText ownername, email, mobile, password, address;
    Button btn_register;
    ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        if (SharedPreferenceManager.getInstance(this).isRegisteresIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
            return;
        }
        loading = findViewById(R.id.loading);

        ownername = findViewById(R.id.ownername);
        email = findViewById(R.id.Email);
        address = findViewById(R.id.Address);
        password = findViewById(R.id.Password);
        btn_register = findViewById(R.id.btn_register);
        mobile = findViewById(R.id.Mobileno);


        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Name =ownername.getText().toString().trim();
                String em =email.getText().toString().trim();
                String add = address.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String mo =mobile.getText().toString().trim();

                if(em.isEmpty()){
                    email.setError("Please Enter Email ID");
                }else if(pass.isEmpty()){
                    password.setError("Please Enter Passsword");
                }else if(Name.isEmpty()){
                    ownername.setError("Please Enter Name");
                }else if(add.isEmpty()){
                    address.setError("Please Enter Address");
                }else  if(mo.isEmpty()){
                    mobile.setError("Please Enter Mobile Number");
                }else   {
                    Regist();
                }
            }
        });


    }

    public void Regist() {
        loading.setVisibility(View.VISIBLE);
        //btn_register.setVisibility(View.GONE);
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || ownername.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || address.getText().toString().isEmpty()) {
            Toast.makeText(Register.this, "Please Fill required Details..", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://krushibazaarofficial1.000webhostapp.com/Register.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Registered successfully")) {
                                loading.setVisibility(View.GONE);
                                btn_register.setVisibility(View.VISIBLE);

                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                                intent.putExtra("email", email.getText().toString().trim());
                                startActivity(intent);
                                finish();

                            } else {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "" + response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.setVisibility(View.GONE);
                    btn_register.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Check Internet Connections !" + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("email", email.getText().toString().trim());
                    param.put("password", password.getText().toString().trim());
                    param.put("owner_name", ownername.getText().toString().trim());
                    param.put("contact", mobile.getText().toString().trim());
                    param.put("address", address.getText().toString().trim());

                    return param;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }


}