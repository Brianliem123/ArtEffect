package com.fa.arteffect.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fa.arteffect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button btnRegister;
    private FirebaseAuth auth;

    Button btnPindahToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        isValidInput();

        //FindViewByID
        btnPindahToLogin = findViewById(R.id.btn_login_InReg);
        //--END--

        btnPindahToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Perintah Pindah intent Eksplist (Agar Berpindah activity satu ke Activity lain)
                Intent intent = new Intent(register.this, login_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //Validasi Field sudah di is atau belum
    private void isValidInput() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextName.getText().toString().trim();
                String userEmail = editTextEmail.getText().toString().trim();
                String userPassword = editTextPassword.getText().toString().trim();
//                String userConfirmPass = editTextConfirmPassword.getText().toString().trim();

                //Validasi email , password , nama , dan confirm password
                if (userName.isEmpty()) {
                    editTextName.setError("Name Cannot Be Empty");
                }
                if (userEmail.isEmpty()) {
                    editTextEmail.setError("email name cannot be empty");
                } else if (!userEmail.contains("@")) {
                    editTextEmail.setError("must be a valid email");
                }
                if (userPassword.isEmpty()) {
                    editTextPassword.setError("Password Harus Diisi");
                } else if (userPassword.length() < 6) {
                    editTextPassword.setError("Password must be 8 or more character");
                }
//                if (userConfirmPass.isEmpty()) {
//                    editTextConfirmPassword.setError("Confirm password cannot be empty");
//                } else if (editTextConfirmPassword.getText().toString().equals(userPassword)) {
//                    editTextConfirmPassword.setError("Password did not match");}
                else {
                    //create user Dengan Firebase Auth
                    auth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //Jika gagal register do something
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        //Jika Sukses Register
                                        Intent intent = new Intent(register.this,login_activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
    public void initView(){
        editTextName = findViewById(R.id.et_Name);
        editTextEmail = findViewById(R.id.et_Email);
        editTextPassword = findViewById(R.id.et_Password);
//        editTextConfirmPassword = findViewById(R.id.et_confirmPassword);
        btnRegister = findViewById(R.id.click_register);
        auth = FirebaseAuth.getInstance();
    }
}

