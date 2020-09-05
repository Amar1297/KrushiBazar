package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    public static final String PRFILE_URL = "https://krushibazaarofficial1.000webhostapp.com/ProfileData.php";
    TextView textshopname, textownername, textemail, textmobile, textaddress, textpassword;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        textemail = findViewById(R.id.email);

        textownername = findViewById(R.id.textviewownwername);

        textmobile = findViewById(R.id.textviewmobile);
        textaddress = findViewById(R.id.textviewadddress);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // FetchData(email);
        Fetch(email);
    }

    private void Fetch(final String email) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, PRFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(Profile.this, ""+response, Toast.LENGTH_SHORT).show();
               String[] str=response.split(",");
                textemail.setText("Email:-"+str[0]);


                textownername.setText("Farmer Name :-"+str[1]);

                textmobile.setText("Mobile No:-"+str[2]);
                textaddress.setText("Address:-"+str[3]);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        requestQueue.add(request);

    }
}





