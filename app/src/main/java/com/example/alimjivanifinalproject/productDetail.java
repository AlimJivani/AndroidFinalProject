package com.example.alimjivanifinalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class productDetail extends AppCompatActivity {

    ImageView mainImage, subImage1, subImage2, subImage3, addToCard;
    TextView detailName, detailDescription, detailPrice;
    Button buyNow;
    DatabaseReference pReference;
    productData pData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mainImage = findViewById(R.id.mainImage);
        subImage1 = findViewById(R.id.subImage1);
        subImage2 = findViewById(R.id.subImage2);
        subImage3 = findViewById(R.id.subImage3);
        detailName = findViewById(R.id.detailName);
        detailDescription = findViewById(R.id.detailDescription);
        detailPrice = findViewById(R.id.detailPrice);
        addToCard = findViewById(R.id.addToCart);

        pReference = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        DatabaseReference productRef = pReference.child("Products").child(productId);

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    pData = snapshot.getValue(productData.class);

                    detailName.setText(pData.getName());
                    detailDescription.setText(pData.getDescription());
                    detailPrice.setText(pData.getPrice());

                    List<String> imageNames = pData.getImage();
                    String mainImg = imageNames.get(0);
                    int mImage = getResources().getIdentifier(mainImg, "drawable", getPackageName());
                    mainImage.setImageResource(mImage);

                    List<ImageView> imageViews = new ArrayList<>();
                    imageViews.add(subImage1);
                    imageViews.add(subImage2);
                    imageViews.add(subImage3);

                    for (int i = 0; i < imageNames.size(); i++) {
                        String imageName = imageNames.get(i);
                        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                        imageViews.get(i).setImageResource(resourceId);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        addToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(productDetail.this, AddToCart.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onImageClick(View view) {
        int viewId = view.getId();
        int newImageResId = 0;

        if (viewId == R.id.subImage1) {
            String image1 = pData.getImage().get(0);
            newImageResId = getResources().getIdentifier(image1, "drawable", getPackageName());
        } else if (viewId == R.id.subImage2) {
            String image2 = pData.getImage().get(1);
            newImageResId = getResources().getIdentifier(image2, "drawable", getPackageName());
        } else if (viewId == R.id.subImage3) {
            String image3 = pData.getImage().get(2);
            newImageResId = getResources().getIdentifier(image3, "drawable", getPackageName());
        }

        mainImage.setImageResource(newImageResId);
    }
}