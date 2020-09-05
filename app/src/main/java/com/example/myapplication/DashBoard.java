package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoard extends AppCompatActivity {
    Button add,update,profile,logout,delete;
 String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        add = findViewById(R.id.add_product);
        update = findViewById(R.id.update_product);
        profile = findViewById(R.id.profile);
        delete=findViewById(R.id.delete_product);
        logout = findViewById(R.id.log_out);
        getSupportActionBar().hide();

        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addpro=new Intent(getApplicationContext(), AddProduct.class);
                addpro.putExtra("email",email);
                startActivity(addpro);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent upd=new Intent(getApplicationContext(), Update.class);
                upd.putExtra("email",email);
                startActivity(upd);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pro=new Intent(getApplicationContext(), Profile.class);
                pro.putExtra("email",email);
                startActivity(pro);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dele=new Intent(getApplicationContext(), Delete.class);
                dele.putExtra("email",email);
                startActivity(dele);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}