package com.example.interiorshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInterfaceActivity extends AppCompatActivity {
    private EditText user_email,user_name;
    private EditText user_password;
    private Button but_save, but_cancel;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinterface);
        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        user_name=findViewById(R.id.user_name);
        but_cancel = findViewById(R.id.user_cancel);
        but_save=findViewById(R.id.user_save);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
            user_email.setText(email);
            user_name.setText(name);
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }
        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInterfaceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
