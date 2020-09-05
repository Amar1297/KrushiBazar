package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Delete extends AppCompatActivity {
    public static final String URL_delete="https://krushibazaarofficial1.000webhostapp.com/DeleteProduct.php";

    public static final String ProName_URL="https://krushibazaarofficial1.000webhostapp.com/ProductName.php";
    Button delete;
    Spinner spinner;
    String email;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);

        delete=findViewById(R.id.Deletedbutton);
        spinner=findViewById(R.id.SpinnerName);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        ListNameProducts(email);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DeleteProduct();
            }
        });
    }
    private void ListNameProducts(final String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ProName_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String[] str=response.split(",");

                adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,str);
                spinner.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Check Your Internet Connection"+error.toString(), Toast.LENGTH_LONG).show();
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
    private void DeleteProduct() {
        final RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST,URL_delete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Delete.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Delete.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<String, String>();
                params.put("name",spinner.getSelectedItem().toString());
                params.put("email", email);




                return params;
            }
        };
        queue.add(request);
    }
}