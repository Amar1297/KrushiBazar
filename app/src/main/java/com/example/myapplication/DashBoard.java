package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DashBoard extends AppCompatActivity {
    Button add,update,profile,logout,delete,deleteAccount;
    String email;
    public   static final String AccountDelete_URl = "https://krushibazaarofficial1.000webhostapp.com/DeleteAccount.php";
    private SharedPreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        add = findViewById(R.id.add_product);
        update = findViewById(R.id.update_product);
        profile = findViewById(R.id.profile);
        delete=findViewById(R.id.delete_product);
        logout = findViewById(R.id.log_out);
        deleteAccount = findViewById(R.id.deleteAccount);
        getSupportActionBar().hide();

        preferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
        /// Intent intent=getIntent();
        email=preferenceManager.getEmail();//intent.getStringExtra("email");



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addpro=new Intent(getApplicationContext(), AddProduct.class);
                startActivity(addpro);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent upd=new Intent(getApplicationContext(), Update.class);
                startActivity(upd);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pro=new Intent(getApplicationContext(), Profile.class);
                startActivity(pro);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dele=new Intent(getApplicationContext(), Delete.class);
                startActivity(dele);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferenceManager preferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
                preferenceManager.setsession(false);
                preferenceManager.setEmail("");
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccount();
            }
        });
    }

    private void DeleteAccount() {

        final RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, AccountDelete_URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DashBoard.this, response, Toast.LENGTH_SHORT).show();

                        SharedPreferenceManager preferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
                        preferenceManager.setsession(false);
                        preferenceManager.setEmail("");
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashBoard.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<String, String>();
                params.put("email",email);
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
        finish();
    }
}