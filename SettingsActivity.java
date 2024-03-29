package com.example.lapitchat;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;


    //Android Layout

    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;


    static final String STATE_USER = "Users";
    private String mUsers;

    // Storage Firebase


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

            mDisplayImage = (CircleImageView) findViewById( R.id.settings_image );
            mName = (TextView) findViewById( R.id.settings_name );
            mStatus = (TextView) findViewById( R.id.settings_status );


            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

            String current_uid = mCurrentUser.getUid();


            mUserDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( current_uid );


            mUserDatabase.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String image =  dataSnapshot.child( "image" ).getValue() .toString();
                    String name =  dataSnapshot.child( "name" ).getValue().toString();
                    String status =  dataSnapshot.child( "status" ).getValue().toString();
                    String thumb_image = dataSnapshot.child( "thumb_image" ).getValue() .toString();
                        mName.setText( name );
                        mStatus.setText( status );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );

        }




}





