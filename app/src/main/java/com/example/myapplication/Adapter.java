package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
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

    Adapter(Context context,List<Product> products){
        this.layoutInflater=LayoutInflater.from(context);
        this.products=products;
        myData=new ArrayList<>(products);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.custom_view,parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.texttitle.setText("Name:-"+products.get(position).getTitle());
        holder.textDesc.setText("Dsc:-"+products.get(position).getDesc());
        holder.textprice.setText("Price:-"+products.get(position).getPrice());
        holder.ratingBar.setRating(Float.parseFloat(products.get(position).getRating()));
        Picasso.get().load(products.get(position).getImage_url()).into(holder.imageView);
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
        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = new Intent(v.getContext(), ProductDetails.class);
                        intent.putExtra("title", (products.get(getAdapterPosition())).getTitle());
                        intent.putExtra("desc", ( products.get(getAdapterPosition())).getDesc());
                        intent.putExtra("price", (products.get(getAdapterPosition())).getPrice());
                        intent.putExtra("bar",(products.get(getAdapterPosition())).getRating());
                        intent.putExtra("url",(products.get(getAdapterPosition())).getImage_url());
                        intent.putExtra("status",(products.get(getAdapterPosition())).getStatus());
                        intent.putExtra("qunt",(products.get(getAdapterPosition())).getQuantity());
                        intent.putExtra("contact",(products.get(getAdapterPosition())).getContact());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(v.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            texttitle=itemView.findViewById(R.id.textTitle);
            textDesc=itemView.findViewById(R.id.textDesc);
            textprice=itemView.findViewById(R.id.textPrice);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            imageView=itemView.findViewById(R.id.imageView3);
        }
    }
}
