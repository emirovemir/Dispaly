package com.example.lapitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateBnt;

    private Toolbar mToolbar;




    //ProgressDialog
        private ProgressDialog mRegProgress;
    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Toolbar Set
        mToolbar = (Toolbar) findViewById( R.id.register_toolbar );
        setSupportActionBar( mToolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Create Account" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        mRegProgress = new ProgressDialog( this );


        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Android Fields
        mDisplayName = (TextInputLayout) findViewById(R.id.reg_display_name);
        mEmail = (TextInputLayout) findViewById(R.id.reg_email);
        mPassword = (TextInputLayout) findViewById(R.id.reg_password);
        mCreateBnt = (Button) findViewById(R.id.reg_create_bth);

        mCreateBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = Objects.requireNonNull( mDisplayName.getEditText() ).getText().toString();
                String email = Objects.requireNonNull( mEmail.getEditText() ).getText().toString();
                String password = Objects.requireNonNull( mPassword.getEditText() ).getText().toString();

                if (!TextUtils.isEmpty( display_name )||!TextUtils.isEmpty( email )|| !TextUtils.isEmpty( password ))
                {
                    mRegProgress.setTitle( "Registering User" );
                    mRegProgress.setMessage( "Please wait while we create your account !" );
                    mRegProgress.setCanceledOnTouchOutside( false );
                    register_user(display_name,email,password);
                }


            }
        });

    }
    private void register_user(final String display_name, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid   = current_user.getUid();

                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                        HashMap<String,String> userMap =  new HashMap<>();
                        userMap.put("name",display_name);
                        userMap.put( "image","default" );
                        userMap.put( "status","Hi there Im using Lapit Chat App." );
                        userMap.put( "thumb_image","default" );
                        mDatabase.setValue(userMap).addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    mRegProgress.dismiss();
                                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                    mainIntent.addFlags(  Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }
                        } );


                    } else {

                        mRegProgress.hide();
                        Toast.makeText(RegisterActivity.this,"Cannot Sign in. Please check the form and try again",Toast.LENGTH_LONG).show();
                    }
                }





        });

    }
}

