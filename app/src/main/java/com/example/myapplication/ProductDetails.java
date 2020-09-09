package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import android.net.Uri;
import android.widget.Toast;

public class ProductDetails extends AppCompatActivity {
    ImageView imageView;
    TextView titl, desc, price, staus, quntity, contact;
    RatingBar ratingBar;
    Button button1, button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        getSupportActionBar().hide();


        try {
            titl = findViewById(R.id.title);
            desc = findViewById(R.id.Desc);
            price = findViewById(R.id.price);
            staus = findViewById(R.id.status);
            ratingBar = findViewById(R.id.Rating);
            imageView = findViewById(R.id.imageView);
            quntity = findViewById(R.id.quntity);
            contact = findViewById(R.id.contact);
            button1 = (Button) findViewById(R.id.wap_btn);
            button2 = (Button) findViewById(R.id.call_btn);


            Intent intent = getIntent();
            String ti = intent.getStringExtra("title");
            String Desc = intent.getStringExtra("desc");
            int pr = intent.getIntExtra("price",0);
            String r = intent.getStringExtra("bar");
            String img = intent.getStringExtra("url");
            String sts = intent.getStringExtra("status");
            int qunt = intent.getIntExtra("qunt",0);
            final String number = intent.getStringExtra("contact");


            titl.setText("Title :- " + ti);
            desc.setText("Description :- " + Desc);
            price.setText("Price    :-  " + pr);
            staus.setText("Status :-" + sts);
            quntity.setText("Quantity :- " + qunt);
            contact.setText("Contact NO. :-" + number);
            ratingBar.setRating(Float.parseFloat(String.valueOf(4.2)));
            Picasso.get().load(img.trim()).into(imageView);

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(ProductDetails.this, "Calling : " + number, Toast.LENGTH_SHORT).show();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + number.trim()));

                    if (ActivityCompat.checkSelfPermission(ProductDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.


                        return;
                    }
                    startActivity(callIntent);

                }


            });


        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void goToSo (View view) {
        String number = contact.getText().toString();
        Toast.makeText(this, "Opening WhatsApp..", Toast.LENGTH_SHORT).show();
        String site = "https://wa.me/"+91+number;

        goToUrl ( site);

    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);



    }





}
