package com.starixc.adminhans;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.starixc.adminhans.Model.Products;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductManageFragment extends Fragment {
    private String categoryName,description,size,price,pName,saveCurrentDate,saveCurrentTime;
    private Button buttonUpdate;
    private ImageView selectProductImage;
    private EditText inputProductName,inputProductSize,inputProductDesc,inputProductPrice;
    private static final int galleryPick =1;
    private Uri imageUri;
    private  String productRandomKey, downloadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productsRef;
    private ProgressDialog loadingBar;
    private View editView;

    private String productID="";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference productRef;
    Products currentProduct;
    public ProductManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       editView= inflater.inflate(R.layout.fragment_product_manage, container, false);
        Bundle bundle = getArguments();

        productID = bundle.getString("pid");
        buttonUpdate=(Button) editView.findViewById(R.id.update_product_btn);
        selectProductImage=(ImageView) editView.findViewById(R.id.select_update_img);
        inputProductName=(EditText) editView.findViewById(R.id.update_product_name);
        inputProductSize=(EditText) editView.findViewById(R.id.update_product_size);
        inputProductDesc=(EditText) editView.findViewById(R.id.update_product_desc);
        inputProductPrice=(EditText) editView.findViewById(R.id.update_product_price);
        getProductDetails(productID);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChange();
            }
        });
       return editView;
        
    }

    private void applyChange() {
        ValidateProductData();
    }
    private void ValidateProductData() {
        Description=InputProductDesc.getText().toString();
        Price=InputProductPrice.getText().toString();
        Size=InputProductSize.getText().toString();
        PName=InputProductName.getText().toString();

        if (ImageUri==null){
            Toast.makeText(getActivity(), "Product image is mandatory", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(getActivity(), "Please Write Product Description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(getActivity(), "Please Write Product Price", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Size))
        {
            Toast.makeText(getActivity(), "Please Write Product Size", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PName))
        {
            Toast.makeText(getActivity(), "Please Write Product Name", Toast.LENGTH_SHORT).show();
        }else{
            StoreProductInformation();
        }


    }
    private void getProductDetails(String productID) {
        productRef=db.collection("Products").document(productID);
        productRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists())
                        {
                            currentProduct = documentSnapshot.toObject(Products.class);
                            // String id = documentSnapshot.getId();
                            // Toast.makeText(getContext(), "Productid :"+id, Toast.LENGTH_SHORT).show();
                            inputProductName.setText(currentProduct.getName());
                            inputProductPrice.setText(currentProduct.getPrice());
                            inputProductDesc.setText(currentProduct.getDescription());
                            inputProductSize.setText(currentProduct.getSize());
                            Picasso.get().load(currentProduct.getImage()).into(selectProductImage);
                        }
                    }
                });
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Products products =dataSnapshot.getValue(Products.class);

                     inputProductName.setText(products.getName());
                    inputProductPrice.setText(products.getPrice());
                    inputProductDesc.setText(products.getDescription());
                    inputProductSize.setText(products.getSize());
                    Picasso.get().load(products.getImage()).into(selectProductImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
