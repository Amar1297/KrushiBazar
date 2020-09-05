package com.example.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
EditText forgetpassword;
Button req_password;
private ProgressDialog mProgress;

//String URL="https://naturomartfinal.000webhostapp.com/forget.php";
//StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        getSupportActionBar().hide();
        forgetpassword=findViewById(R.id.forgetpassword);
        req_password=findViewById(R.id.req_password);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        req_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();
                forgetpassword();
            }
        });
    }

    public void forgetpassword()
    {
        StringRequest request=new StringRequest(Request.Method.POST, "https://krushibazaarofficial1.000webhostapp.com/forget.php", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                if (response.contains("SUCCESS"))
                {
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Password Is Sent to Your Mail ",Toast.LENGTH_LONG).show();
                }else {
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Invalid Email ",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params=new HashMap<>();
                params.put("email",forgetpassword.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
