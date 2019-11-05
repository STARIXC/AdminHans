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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
public class AddCategoryFragment extends Fragment {
    private String categoryName,saveCurrentDate,saveCurrentTime;
    private Button addNewCategoryBtn;
    private ImageView selectCategoryImage;
    private EditText inputCategoryName;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String categoryRandomKey, downloadImageUri;
    DatabaseReference categoryRef;
    private StorageReference catImageRef;
    private ProgressDialog loadingBar;
    private View addView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addView = inflater.inflate(R.layout.fragment_add_category, container, false);
        catImageRef = FirebaseStorage.getInstance().getReference().child("Category Images");
        addNewCategoryBtn = (Button) addView.findViewById(R.id.add_new_category);
        selectCategoryImage = (ImageView) addView.findViewById(R.id.select_category_img);
        inputCategoryName = (EditText) addView.findViewById(R.id.category_name);

        selectCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        addNewCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateCategoryData();
            }
        });
        // Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        return addView;
    }

    private void validateCategoryData() {
        categoryName = inputCategoryName.getText().toString();
        if (imageUri == null) {
            Toast.makeText(getActivity(), "Please Select an Image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(categoryName)) {
            Toast.makeText(getActivity(), "Please Write the Category Name", Toast.LENGTH_SHORT).show();
        }else {
            storeCategoryInformation();
        }
    }

    private void storeCategoryInformation() {
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYY");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        categoryRandomKey= saveCurrentDate +saveCurrentTime;
        final StorageReference filepath = catImageRef.child(imageUri.getLastPathSegment()+categoryRandomKey +".jpg");
        final UploadTask uploadTask= filepath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(getContext(), "Error: " +message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Category Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUri=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUri=task.getResult().toString();
                            Toast.makeText(getContext(), "Getting category image success", Toast.LENGTH_SHORT).show();
                            saveCategoryInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveCategoryInfoToDatabase() {
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("cid",categoryRandomKey);
        categoryMap.put("name",categoryName);
        categoryMap.put("image",downloadImageUri);

        db.collection("Category")
                .add(categoryMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //loadingBar.dismiss();
                        Toast.makeText(getActivity(), "Category added successfully", Toast.LENGTH_SHORT).show();
                        FragmentManager fm=getActivity().getSupportFragmentManager();
                        FragmentTransaction ft =fm.beginTransaction();
                       CategoryFragment categoryFragment= new CategoryFragment();

                        ft.replace(R.id.fragment,categoryFragment).addToBackStack(null);
                        ft.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getActivity(), "Error: "+e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
           selectCategoryImage.setImageURI(imageUri);
        }
    }

}
