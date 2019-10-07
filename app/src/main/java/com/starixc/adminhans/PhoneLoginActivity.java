package com.starixc.adminhans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity   {
    private FirebaseAuth mAuth;
    private EditText editTextPhone, editTextVerificationCode;
    private ProgressDialog progressBar;
    private Button sendverfication, verifybtn;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextVerificationCode = (EditText) findViewById(R.id.editTextVerify);
        progressBar = new ProgressDialog(this);

        sendverfication=(Button) findViewById(R.id.buttonSendVerification);
       verifybtn=(Button) findViewById(R.id.buttonVerification);

       sendverfication.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               
               
               String phoneNumber=editTextPhone.getText().toString();
               if (TextUtils.isEmpty(phoneNumber))
               {
                   Toast.makeText(PhoneLoginActivity.this, "Please Provide your phone Number", Toast.LENGTH_SHORT).show();
               }
               else{
                   progressBar.setCanceledOnTouchOutside(false);
                   progressBar.setTitle("Sending Verification Code");
                   progressBar.setMessage("Please wait while we verify your phone number");
                   progressBar.show();
                   PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           phoneNumber,        // Phone number to verify
                           60,                 // Timeout duration
                           TimeUnit.SECONDS,   // Unit of timeout
                           PhoneLoginActivity.this,               // Activity (for callback binding)
                           mcallbacks);        // OnVerificationStateChangedCallbacks
               }
           }
       });
       verifybtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               editTextPhone.setVisibility(View.INVISIBLE);
               sendverfication.setVisibility(View.INVISIBLE);

               String verificationCode= editTextVerificationCode.getText().toString();
               if (TextUtils.isEmpty(verificationCode))
               {
                   Toast.makeText(PhoneLoginActivity.this, "Please Enter the verification Code", Toast.LENGTH_SHORT).show();
               }
               else
                   {

                       progressBar.setTitle("Code Verification");
                       progressBar.setMessage("Please wait while we verify your phone number");
                       progressBar.setCanceledOnTouchOutside(false);
                       progressBar.show();
                       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                       signInWithPhoneAuthCredential(credential);
                   }
           }
       });
       mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
           @Override
           public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
           }

           @Override
           public void onVerificationFailed(@NonNull FirebaseException e) {
               progressBar.dismiss();
               Toast.makeText(PhoneLoginActivity.this, "Invalid phone numberPlease , Please enter Phone number beginning with the country Code(+254)", Toast.LENGTH_SHORT).show();
               editTextPhone.setVisibility(View.VISIBLE);
               sendverfication.setVisibility(View.VISIBLE);
               editTextVerificationCode.setVisibility(View.INVISIBLE);
               verifybtn.setVisibility(View.INVISIBLE);
           }
           @Override
           public void onCodeSent(@NonNull String verificationId,
                                  @NonNull PhoneAuthProvider.ForceResendingToken token) {
               progressBar.dismiss();

               // Save verification ID and resending token so we can use them later
               mVerificationId = verificationId;
               mResendToken = token;

               Toast.makeText(PhoneLoginActivity.this, "Code has been sent, Please check you SMS Folder", Toast.LENGTH_SHORT).show();
               editTextPhone.setVisibility(View.INVISIBLE);
               sendverfication.setVisibility(View.INVISIBLE);
               editTextVerificationCode.setVisibility(View.VISIBLE);
               verifybtn.setVisibility(View.VISIBLE);

           }
       };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                progressBar.dismiss();
                            Toast.makeText(PhoneLoginActivity.this, "Congratulation you have logged in successfully", Toast.LENGTH_SHORT).show();
                            goToHome();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void goToHome() {
        Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}
