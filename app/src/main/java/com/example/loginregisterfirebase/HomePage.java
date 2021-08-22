package com.example.loginregisterfirebase;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.loginregisterfirebase.logic.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity{

    private final String TAG = "HOME_PAGE_ACTIVITY";
    public static final String USER = "user";

    UserViewModel userViewModel;
    private User currentUser;

    private Handler handler;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Initialize Bottom Navigation View.
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);

        //Initialize NavController.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser();

        handler = new Handler();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}