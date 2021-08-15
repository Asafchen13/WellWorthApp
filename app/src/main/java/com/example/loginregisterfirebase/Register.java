package com.example.loginregisterfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    // create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://wellworthapp-b2ced-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullName = findViewById(R.id.fullName);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final EditText conPassword = findViewById(R.id.conPassword);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNow);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get data from EditTexts into String variables
                final String fullNameTxt = fullName.getText().toString();
                final String emailTxt = email.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();


                // check if user fill all the fields before sending data to firebase
                if (fullNameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // check if password are matching with each other
                // if not matching with each other then show a toast message
                else if (!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(Register.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                }

                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // check if email is not registered before
                            if (snapshot.hasChild(emailTxt)){
                                Toast.makeText(Register.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                // sending data to firebase Realtime Database
                                // we are using email Address as unique identity of every user
                                databaseReference.child("users").child(emailTxt).child("fullName").setValue(fullNameTxt);
                                databaseReference.child("users").child(emailTxt).child("phone").setValue(fullNameTxt);
                                databaseReference.child("users").child(emailTxt).child("password").setValue(fullNameTxt);

                                // show a success message then finish the activity
                                Toast.makeText(Register.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}