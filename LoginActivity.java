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

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Button mLogin_btn;

    private Toolbar mToolbar;

    private ProgressDialog mLoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById( R.id.login_toolbar );
        setSupportActionBar( mToolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Login" );




        mLoginProgress = new ProgressDialog( this );


        mLoginEmail = (TextInputLayout) findViewById( R.id.login_email );
        mLoginPassword = (TextInputLayout) findViewById( R.id.login_password );
        mLogin_btn = (Button) findViewById( R.id.login_btn );

        mLogin_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty( email )||!TextUtils.isEmpty( password ))
                {
                    mLoginProgress.setTitle( "Logging In" );
                    mLoginProgress.setMessage( "Please wait while we check your credentials." );
                    mLoginProgress.setCanceledOnTouchOutside( false );
                    mLoginProgress.show();

                    loginUser(email,password);
                }

            }
        } );

    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    mLoginProgress.dismiss();
                    Intent mainIntent  = new Intent( LoginActivity.this,MainActivity.class );
                    mainIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( mainIntent );
                    finish();
                }else
                {
                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this,"Cannot Sign in. Please check the form and try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
