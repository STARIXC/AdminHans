package com.starixc.adminhans;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {
    private String categoryName,Description,Size,Price,PName,saveCurrentDate,saveCurrentTime;
    private Button AddnewProductBtn;
    private ImageView SelectProductImage;
    private EditText InputProductName,InputProductSize,InputProductDesc,InputProductPrice;
    private static final int GalleryPick =1;
    private Uri ImageUri;
    private  String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private View addView;
    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addView= inflater.inflate(R.layout.fragment_add_product, container, false);
        Bundle bundle = getArguments();

       categoryName = bundle.getString("category");
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        AddnewProductBtn=(Button) addView.findViewById(R.id.add_new_product);
        SelectProductImage=(ImageView) addView.findViewById(R.id.select_product_img);
        InputProductName=(EditText) addView.findViewById(R.id.product_name);
        InputProductSize=(EditText) addView.findViewById(R.id.product_size);
        InputProductDesc=(EditText) addView.findViewById(R.id.product_desc);
        InputProductPrice=(EditText) addView.findViewById(R.id.product_price);
        //loadingBar= new ProgressDialog(this);

        SelectProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        AddnewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });


            Toast.makeText(getActivity(), categoryName, Toast.LENGTH_SHORT).show();
        return addView;

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

    private void StoreProductInformation() {
        //loadingBar.setTitle("Adding New Product");
        //loadingBar.setMessage("Please wait, while we add the new Product ...");
       // loadingBar.setCanceledOnTouchOutside(false);
       // loadingBar.show();

        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYY");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey= saveCurrentDate +saveCurrentTime;
        final StorageReference filepath=ProductImagesRef.child(ImageUri.getLastPathSegment() +productRandomKey +".jpg");

        final UploadTask uploadTask= filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getActivity(), "Error: " +message, Toast.LENGTH_SHORT).show();
              //  loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(getActivity(), "Product ImageUploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl=task.getResult().toString();
                           Toast.makeText(getActivity(), "getting product image url success", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void saveProductInfoToDatabase() {
        HashMap<String, Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",categoryName);
        productMap.put("name",PName);
        productMap.put("price",Price);
        productMap.put("size",Size);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_SHORT).show();
                            FragmentManager fm=getActivity().getSupportFragmentManager();
                           FragmentTransaction ft =fm.beginTransaction();
                            ProductsFragment productFragment= new ProductsFragment();

                            ft.replace(R.id.fragment,productFragment).addToBackStack(null);
                            ft.commit();
                            //Intent intent = new Intent(AdminAddNewProduct.this, AdminDashBoard.class);
                            //startActivity(intent);
                           // loadingBar.dismiss();

                        }else{
                          //  loadingBar.dismiss();
                         String message=task.getException().toString();
                         Toast.makeText(getActivity(), "Error: "+message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            SelectProductImage.setImageURI(ImageUri);
        }
    }
}
