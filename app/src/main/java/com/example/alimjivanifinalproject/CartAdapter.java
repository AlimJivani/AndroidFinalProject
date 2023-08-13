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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private List<CartItem> cartItems;
    private AddToCart addToCartActivity;

    public CartAdapter(List<CartItem> cartItems,  AddToCart activity) {
        this.cartItems = cartItems;
        this.addToCartActivity = activity;
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
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        holder.productPrice.setText(String.valueOf(decimalFormat.format(cartItem.getPrice() * cartItem.getQuantity())));
        holder.totalItemsCount.setText(String.valueOf(cartItem.getQuantity()));

        holder.addItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.increaseQuantity();
                saveCartItemsToFirestore();
                notifyDataSetChanged();
                updatePaymentTotal();
            }
        });

        holder.removeItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.decreaseQuantity();
                saveCartItemsToFirestore();
                notifyDataSetChanged();
                updatePaymentTotal();
            }
        });

    }

    private void updatePaymentTotal() {
        addToCartActivity.updatePaymentTotal();
    }

    public void saveCartItemsToFirestore() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cartItemsRef = database.getReference("cartItems");

        for (CartItem cartItem : cartItems) {
            DatabaseReference cartItemRef = cartItemsRef.child(cartItem.getName());

            cartItemRef.child("name").setValue(cartItem.getName());
            cartItemRef.child("price").setValue(cartItem.getPrice());
            cartItemRef.child("quantity").setValue(cartItem.getQuantity());
            cartItemRef.child("image").setValue(cartItem.getImage());
            cartItemRef.child("id").setValue(cartItem.getId());
        }

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
