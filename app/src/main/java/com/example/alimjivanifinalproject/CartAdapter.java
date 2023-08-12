package com.example.alimjivanifinalproject;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        CartItem cartItem = cartItems.get(position);

        String imageName = cartItem.getImage();
        int imageResourceId1 = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        holder.productImage.setImageResource(imageResourceId1);

        holder.productName.setText(cartItem.getName());
        holder.productPrice.setText(String.valueOf(cartItem.getPrice()));

        holder.totalItemsCount.setText(String.valueOf(cartItem.getQuantity()));

        holder.addItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.increaseQuantity();
                notifyDataSetChanged();
            }
        });

        holder.removeItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.decreaseQuantity();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView totalItemsCount;
        TextView addItemsButton;
        TextView removeItemsButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            totalItemsCount = itemView.findViewById(R.id.totalItemsCount);
            addItemsButton = itemView.findViewById(R.id.addItemsButton);
            removeItemsButton = itemView.findViewById(R.id.removeItemsButton);
        }
    }
}
