package com.example.loginregisterfirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.managers.AuthenticationManager;
import com.example.loginregisterfirebase.managers.DatabaseManager;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AuthenticationManager myAuthenticationManager;
    private DatabaseManager myDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAuthenticationManager = AuthenticationManager.getInstance();

        myDatabaseManager = DatabaseManager.getInstance();

        FirebaseUser user = myAuthenticationManager.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
    }
}