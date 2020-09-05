package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    Button buttonvendor, buttoncustomer,mainBtn;
    ToggleButton toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        buttonvendor = findViewById(R.id.start_btn);
        buttoncustomer = findViewById(R.id.customer);
        mainBtn = findViewById(R.id.btn);
        ToggleButton toggle = findViewById(R.id.btn);
        buttoncustomer.setVisibility(View.GONE);


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    buttonvendor.setVisibility(View.VISIBLE);
                    buttoncustomer.setVisibility(View.GONE);
                    // The toggle is enabled
                } else {
                    // The toggle is disabled

                    buttonvendor.setVisibility(View.GONE);
                    buttoncustomer.setVisibility(View.VISIBLE);
                }
            }
        });
mainBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});

        buttonvendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));}
        });

        buttoncustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), ProductsRecyclerView.class));
            }
        });
    }

}
