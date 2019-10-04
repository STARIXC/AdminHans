package com.starixc.adminhans;



import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Products;


public class ProductDetailsActivity extends AppCompatActivity {
    private TextView addToCartBtn,buyNowBtn;
    private ImageView productImage,addToFavourites;
    private ElegantNumberButton numberButton;

    private TextView productPrice,productDescription,productName,productSize;
    private String productID="",saveCurrentDate,saveCurrentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        productID= getIntent().getStringExtra("pid");


        buyNowBtn=(TextView) findViewById(R.id.buyNowBtn);
        addToCartBtn=(TextView) findViewById(R.id.btn_add_to_cart);
        addToFavourites=(ImageView) findViewById(R.id.btn_fav);
        numberButton =(ElegantNumberButton) findViewById(R.id.quantity_btn);
        productImage=(ImageView) findViewById(R.id.product_image_detail);
        productPrice=(TextView) findViewById(R.id.product_price_detail);
        productDescription=(TextView) findViewById(R.id.product_desc_detail);
        productName=(TextView) findViewById(R.id.product_name_detail);
        productSize=(TextView) findViewById(R.id.product_size_detail);

        getProductDetails(productID);


    }



    private void getProductDetails(String productID) {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Products products =dataSnapshot.getValue(Products.class);



                    productName.setText(products.getName());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    productSize.setText(products.getSize());
                    Picasso.get().load(products.getImage()).into(productImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
