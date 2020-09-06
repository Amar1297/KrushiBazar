package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    private LayoutInflater layoutInflater;
    List<Product> products;
    List<Product> myData;
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private GridLayoutManager mLayoutManager;

    public Adapter(List<Product> products1,GridLayoutManager layoutManager) {
        this.products=products1;
        mLayoutManager = layoutManager;
        myData=new ArrayList<>(products);
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }
    }
    Adapter(Context context,List<Product> products){
        this.layoutInflater=LayoutInflater.from(context);
        this.products=products;
        myData=new ArrayList<>(products);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view, parent, false);
        }
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product item = products.get(position % 4);
        holder.texttitle.setText("Name:-" + item.getTitle());
        Picasso.get().load(item.getImage_url()).into(holder.imageView);
        if (getItemViewType(position) == VIEW_TYPE_BIG) {
            holder.textDesc.setText("Dsc:-" + item.getDesc());
            holder.textprice.setText("Price:-" + item.getPrice());
            holder.ratingBar.setRating((float) 4.2);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public Filter getFilter() {
        return Demofilter;
    }

    public Filter Demofilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filterdata=new ArrayList<>();
            if(constraint==null||constraint.length()==0)
            {
                filterdata.addAll(myData);
            }else {
                String pattern=constraint.toString().toLowerCase().trim();

                for (Product item:myData){
                    if(item.getTitle().toLowerCase().contains(pattern)){
                        filterdata.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterdata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products.clear();
            products.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView texttitle,textDesc,textprice;
        RatingBar ratingBar;
        ImageView imageView;
        ViewHolder(View itemView,int viewtype) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = new Intent(v.getContext(), ProductDetails.class);
                        intent.putExtra("title", (products.get(getAdapterPosition())).getTitle());
                        intent.putExtra("desc", (products.get(getAdapterPosition())).getDesc());
                        intent.putExtra("price", (products.get(getAdapterPosition())).getPrice());
                        intent.putExtra("bar", (products.get(getAdapterPosition())).getRating());
                        intent.putExtra("url", (products.get(getAdapterPosition())).getImage_url());
                        intent.putExtra("status", (products.get(getAdapterPosition())).getStatus());
                        intent.putExtra("qunt", (products.get(getAdapterPosition())).getQuantity());
                        intent.putExtra("contact", (products.get(getAdapterPosition())).getContact());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (viewtype == VIEW_TYPE_BIG) {
                texttitle = itemView.findViewById(R.id.textTitle);
                textDesc = itemView.findViewById(R.id.textDesc);
                textprice = itemView.findViewById(R.id.textPrice);
                ratingBar = itemView.findViewById(R.id.ratingBar);
                imageView = itemView.findViewById(R.id.imageView3);
            }else {
                imageView=itemView.findViewById(R.id.image_small);
                texttitle=itemView.findViewById(R.id.title_small);
            }
        }
    }
}
