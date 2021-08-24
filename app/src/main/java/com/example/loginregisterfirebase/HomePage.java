package com.example.loginregisterfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.loginregisterfirebase.dialogs.ChangePasswordDialog;
import com.example.loginregisterfirebase.logic.User;
import com.example.loginregisterfirebase.managers.AuthenticationManager;
import com.example.loginregisterfirebase.viewModels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private final String TAG = "HOME_PAGE_ACTIVITY";
    public static final String USER = "user";

    private Toolbar toolbar;
    UserViewModel userViewModel;
    private User currentUser;


    private Handler handler;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.change_password:

                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.setCancelable(true);
                changePasswordDialog.show(getSupportFragmentManager(), "change password dialog");
                return true;
            case R.id.logout:
                AuthenticationManager.getInstance().signOut();
                Intent intent = new Intent(HomePage.this, Login.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_actions_menu, menu);

        return true;
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