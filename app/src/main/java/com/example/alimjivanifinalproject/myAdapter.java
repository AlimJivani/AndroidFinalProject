package com.example.alimjivanifinalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    Context context;
    ArrayList<productData> products;
    RecyclerView recyclerView;

    public myAdapter(Context context, ArrayList<productData> products, RecyclerView recyclerView) {
        this.context = context;
        this.products = products;
        this.recyclerView = this.recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.product, parent, false);

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = recyclerView.getChildAdapterPosition(v);
//                if (position != RecyclerView.NO_POSITION) {
//                    productData clickedProduct = products.get(position);
//                    Intent intent = new Intent(context, productDetail.class);
//                    intent.putExtra("product_id", clickedProduct.getId());
//                    context.startActivity(intent);
//                }
//            }
//        });


        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        productData pData = products.get(position);

        int resourceId = context.getResources().getIdentifier(pData.getImage(), "drawable", context.getPackageName());

        holder.productImage.setImageResource(resourceId);
        holder.productName.setText(pData.getName());

        String limitedDescription = pData.getLimitedDescription(15);
        holder.productDescription.setText(limitedDescription);

        holder.productPrice.setText(pData.getPrice());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName, productDescription, productPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }

    public void addProducts(ArrayList<productData> newProducts) {
        int startPosition = products.size();
        products.addAll(newProducts);
        notifyItemRangeInserted(startPosition, newProducts.size());
    }
}
