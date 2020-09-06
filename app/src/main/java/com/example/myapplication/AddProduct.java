package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity{
    private static final int PICK_IMAGE_REQUEST = 777;
    private EditText contact, name, description, price, quantity,pname;
    private ImageView imageview;
    private Button choose;
    private RadioGroup radioGroup;
    private RadioButton status;
    private int selectedId;
    private Bitmap bitmap;
    private Uri path;
    private ProgressBar progressBar;
    private static String URL_PRODUCT = "https://krushibazaarofficial1.000webhostapp.com/AddProducts.php";
    //private static String image_name = new Date().getTime()+"";
  String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        getSupportActionBar().hide();
        contact = findViewById(R.id.contact);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        radioGroup = findViewById(R.id.statusgroup);
        imageview = findViewById(R.id.imageview);
        choose = findViewById(R.id.choose);
        progressBar=findViewById(R.id.progressBar);
  progressBar.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        Email=intent.getStringExtra("email");


        choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String n = name.getText().toString();
                    String des = description.getText().toString();
                    String p = price.getText().toString();
                    String ste = status.getText().toString();
                    String s = quantity.getText().toString();
                    String number = contact.getText().toString();

                    if (n.isEmpty() || des.isEmpty() || p.isEmpty()||ste.isEmpty()||s.isEmpty()||number.isEmpty()) {
                        Toast.makeText(AddProduct.this, "Enter Above Filleds....", Toast.LENGTH_SHORT).show();
                    } else if(number.length()!=10){
                        Toast.makeText(AddProduct.this, "Enter 10 Digit Contact Number Only....", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                        gallery.setType("image/*");
                        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
                    }
                }

        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedId = radioGroup.getCheckedRadioButtonId();
        status = findViewById(selectedId);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageview.setImageBitmap(bitmap);
                progressBar.setVisibility(View.VISIBLE);
                choose.setVisibility(View.INVISIBLE);
                makeRequest();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL_PRODUCT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),DashBoard.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),DashBoard.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),DashBoard.class);
                startActivity(intent);
                finish();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email",Email);
                map.put("name", name.getText().toString());
                map.put("description", description.getText().toString());
                map.put("price", price.getText().toString());
                map.put("status", status.getText().toString());
                map.put("quantity", quantity.getText().toString());
                map.put("contact", contact.getText().toString());
                pname = findViewById(R.id.name);
                map.put("image_name", Email+pname.getText().toString());
              map.put("image", imageToString(bitmap));
                return map;
            }
        };
        requestQueue.add(request);
    }
}
