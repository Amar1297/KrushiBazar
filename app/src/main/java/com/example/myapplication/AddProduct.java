package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
   LocationManager locationManager;
    private String latitude="", longitude="";
    private static  final int REQUEST_LOCATION=1;


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

            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            getlocbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    {
                        OnGPS();
                    }
                    else
                    {
                        getLocation();
                    }
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


                    if (Name.isEmpty() || Desc.isEmpty() || Price.isEmpty() || Quntity.isEmpty() || Contect.isEmpty()) {
                        if (Name.isEmpty())
                            name.setError("Enter Name");
                        if (Desc.isEmpty())
                            description.setError("Enter Description");

                        if (Price.isEmpty())
                            price.setError("Enter Price");

                        if (Quntity.isEmpty())
                            quantity.setError("Enter Quantity");
                        if (Contect.length() != 10) {
                            contact.setError("Enter 10 digit only");
                        }
                    } else if (Contect.length() != 10) {
                        contact.setError("Enter 10 digit only");
                    }
                    else if (longitude.length() < 3 || latitude.length() < 3) {
                        Toast.makeText(AddProduct.this, "Share Location Please...", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                        gallery.setType("image/*");
                        startActivityForResult(gallery, PICK_IMAGE_REQUEST);

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
                 makeRequest();
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
                map.put("latitude", latitude.trim());
                map.put("longitude", longitude.trim());
                return map;
            }
        };
        requestQueue.add(request);
    }


    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(AddProduct.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddProduct.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

            }
            else if (LocationPassive !=null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
