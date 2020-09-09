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
    private EditText contact, name, description, price, quantity;
    private ImageView imageview;
    private Button getlocbtn, selectfile;
    private RadioGroup radioGroup;
    private RadioButton status;
    private int selectedId;
    private Bitmap bitmap;
    private Uri path;
    private ProgressBar progressBar;
    private static String URL_PRODUCT = "https://krushibazaarofficial1.000webhostapp.com/AddProducts.php";
    private String latitude="" , longitude="" ;


    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        getSupportActionBar().hide();
        try {
            selectfile = findViewById(R.id.selectfile);
            contact = findViewById(R.id.contact);
            name = findViewById(R.id.name);
            description = findViewById(R.id.description);
            price = findViewById(R.id.price);
            quantity = findViewById(R.id.quantity);
            radioGroup = findViewById(R.id.statusgroup);
            imageview = findViewById(R.id.imageview);
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
//            Intent intent = getIntent();
//            Email = intent.getStringExtra("email");
            getlocbtn = findViewById(R.id.getlocation);
            SharedPreferenceManager preferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
            Email = preferenceManager.getEmail();

            getlocbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                }
            });
            selectfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Name = name.getText().toString().trim();
                    String Desc = description.getText().toString().trim();
                    String Price = price.getText().toString().trim();
                    String Quntity = quantity.getText().toString().trim();
                    String Contect = contact.getText().toString().trim();


//                    if (Name.isEmpty() || Desc.isEmpty() || Price.isEmpty() || Quntity.isEmpty() || Contect.isEmpty()) {
//                        if (Name.isEmpty())
//                            name.setError("Enter Name");
//                        if (Desc.isEmpty())
//                            description.setError("Enter Description");
//
//                        if (Price.isEmpty())
//                            price.setError("Enter Price");
//
//                        if (Quntity.isEmpty())
//                            quantity.setError("Enter Quantity");
//                        if (Contect.length() != 10) {
//                            contact.setError("Enter 10 digit only");
//                        }
//                    } else if (Contect.length() != 10) {
//                        contact.setError("Enter 10 digit only");
//                    }
                    if (longitude.length() > 3 || latitude.length()>3) {
                        Toast.makeText(AddProduct.this, "Share Location Please...", Toast.LENGTH_SHORT).show();
                    } else {
//                        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
//                        gallery.setType("image/*");
//                        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
                        Toast.makeText(AddProduct.this,latitude+longitude, Toast.LENGTH_SHORT).show();
                        contact.setText(latitude+longitude);
                    }
                }


            });
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedId = radioGroup.getCheckedRadioButtonId();
        status = findViewById(selectedId);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageview.setImageBitmap(bitmap);
                progressBar.setVisibility(View.VISIBLE);
                selectfile.setVisibility(View.INVISIBLE);
                // makeRequest();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private String imageToString(Bitmap bitmap) {
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
                    Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                startActivity(intent);
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", Email);
                map.put("name", name.getText().toString().trim());
                map.put("description", description.getText().toString().trim());
                map.put("price", price.getText().toString().trim());
                map.put("status", status.getText().toString().trim());
                map.put("quantity", quantity.getText().toString().trim());
                map.put("contact", contact.getText().toString().trim());
                map.put("image_name", Email + name.getText().toString().trim());
                map.put("image", imageToString(bitmap).trim());
                map.put("latitude", latitude);
                map.put("longitude", longitude);
                return map;
            }
        };
        requestQueue.add(request);
    }

//    private void getLoction() {
//
//        final LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(1000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        if (androidx.core.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        LocationServices.getFusedLocationProviderClient(AddProduct.this)
//                .requestLocationUpdates(locationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        super.onLocationResult(locationResult);
//                        LocationServices.getFusedLocationProviderClient(AddProduct.this)
//                                .removeLocationUpdates(this);
//                        if (locationRequest != null && locationResult.getLocations().size()>0){
//                            int locaindex = locationResult.getLocations().size()-1;
//
//                            double lat = locationResult.getLocations().get(locaindex).getLatitude();
//
//                            double longi = locationResult.getLocations().get(locaindex).getLongitude();
//
//                            Toast.makeText(AddProduct.this, String.format("latitude=%s \n Longitude = %s",lat,longi), Toast.LENGTH_SHORT).show();
//
//                             latitude = String.valueOf(lat);
//                             longitude = String.valueOf(longi);
//
//                        }
//                    }
//                }, Looper.getMainLooper());
//    }

}
