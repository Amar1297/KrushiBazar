package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.myapplication.Adapter.SPAN_COUNT_ONE;
import static com.example.myapplication.Adapter.SPAN_COUNT_THREE;

public class ProductsRecyclerView extends AppCompatActivity {
    private static final String PRODUCT_URL = "https://krushibazaarofficial1.000webhostapp.com/ProductsDisplay.php";
    RecyclerView recyclerView;
    List<Product> products;
    Adapter adapter;
    private ProgressBar progressBar;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        recyclerView = findViewById(R.id.recycle);
        products = new ArrayList<>();
        progressBar=findViewById(R.id.recycleprogressbar);
        progressBar.setVisibility(View.VISIBLE);
        extractProduct();


        gridLayoutManager = new GridLayoutManager(getApplicationContext(),SPAN_COUNT_ONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchitem = menu.findItem(R.id.demo);
        SearchView searchView = (SearchView) searchitem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Gridview) {
            switchLayout();
            switchIcon(item);
            return true;
        }

        if(item.getItemId() == R.id.sortbypriceAsc){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product product, Product product1) {
                    int i=product.getPrice();
                    int j=product1.getPrice();
                    int c=0;
                    if(i>j) c=0;
                    else c=-1;
                    return c;
                }
            });
            refresh();
        }

        if(item.getItemId() == R.id.sortbypricebyDesc){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product product, Product product1) {
                    int i=product.getPrice();
                    int j=product1.getPrice();
                    int c=0;
                    if(i<j) c=0;
                    else c=-1;
                    return c;
                }
            });
            refresh();
        }

        if(item.getItemId() == R.id.sortbyquantity){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product product, Product product1) {
                    int i=product.getQuantity();
                    int j=product1.getQuantity();
                    int c=0;
                    if(i>j) c=0;
                    else c=-1;
                    return c;
                }
            });
            refresh();
        }

        if(item.getItemId() == R.id.sortbyName){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product product, Product product1) {
                    String i=product.getTitle();
                    String j=product1.getTitle();
                    return i.compareTo(j);
                }
            });
            refresh();
        }

        if(item.getItemId() == R.id.sortbynearby){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product product, Product product1) {

                    double i=product.getLatitude(),i1=product1.getLatitude();
                    double j=product.getLongitude(),j1=product1.getLongitude();

//                    double earthRadius = 3958.75;
//
//                    double dLat = Math.toRadians(i1-i);
//                    double dLog = Math.toRadians(j1-j);
//
//                    double sindlat = Math.sin(dLat/2);
//                    double sindlog = Math.sin(dLog/2);
//
//                    double a =Math.pow(sindlat,2)+Math.pow(sindlog,2)
//                            +Math.cos(Math.toRadians(i))*Math.cos(Math.toRadians(i1));
//
//                    double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
//
//                    double distance = earthRadius * c;
//
//                    return (int) distance;
                    int c=0;
                    if(i>j && i1>j1)
                        c=0;
                    else
                        c=-1;

                    return c;
                }
            });
            refresh();
            }

        return super.onOptionsItemSelected(item);
    }




    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
        }
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
        }
    }

    private void extractProduct() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ProductArray=new JSONArray(response);
                    products.clear();
                    for(int i=0;i<ProductArray.length();i++){
                        JSONObject productObject=ProductArray.getJSONObject(i);

                        String email=productObject.getString("email");
                        String desc=productObject.getString("description");
                        String pname=productObject.getString("pname");
                        int price=productObject.getInt("price");
                        String contact=productObject.getString("contact");
                        String status=productObject.getString("status");
                        int quantity=productObject.getInt("quantity");
                        String image_url=productObject.getString("path");
                        double lati = Double.parseDouble(productObject.getString("latitude"));
                        double longi = Double.parseDouble(productObject.getString("longitude"));


                        Product product=new Product(email,pname,desc,price,status,quantity,image_url,contact,lati,longi);
                        products.add(product);
                    }


                    refresh();

                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(ProductsRecyclerView.this, e.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductsRecyclerView.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
        finish();

        products.clear();
    }


    public  void refresh(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new Adapter(getApplicationContext(),products);
        recyclerView.setAdapter(adapter);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), SPAN_COUNT_ONE);
        adapter = new Adapter(products, gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
