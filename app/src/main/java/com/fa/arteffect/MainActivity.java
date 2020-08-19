package com.fa.arteffect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fa.arteffect.Auth.login_activity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    Button btnLogout;
    FirebaseUser mFirebaseUser;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btn_Logout);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent =new Intent(MainActivity.this,login_activity.class);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                updateUI(null);
//                finish();
                logOut();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseUser = fAuth.getCurrentUser();
        if (mFirebaseUser!=null){
           /* Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);*/
        }else {
            Intent intent = new Intent(getApplicationContext(), login_activity.class);
            startActivity(intent);
            finish();
        }
    }
    public void logOut(){
        fAuth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(null);
            }

        });
    }
    private void updateUI(FirebaseUser user) {
        if (user==null){
            Intent intent = new Intent(getApplicationContext(), login_activity.class);
            startActivity(intent);
            finish();
        }
    }
}