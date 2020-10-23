package com.asrul.revoprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.asrul.revoprint.adapter.ProdukAdapter;
import com.asrul.revoprint.model.produk.DataProduk;
import com.asrul.revoprint.ui.AccountFragment;
import com.asrul.revoprint.ui.CartFragment;
import com.asrul.revoprint.ui.FavoriteFragment;
import com.asrul.revoprint.ui.HomeFragment;
import com.asrul.revoprint.ui.login.LoginActivity;
import com.asrul.revoprint.viewmodel.ProdukViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isLoggedIn()){
            moveToLoginActivity();
        }

        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void moveToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home_nav:
                fragment = new HomeFragment();
                break;
            case R.id.fav_nav:
                fragment = new FavoriteFragment();
                break;
            case R.id.cart_nav:
                fragment = new CartFragment();
                break;
            case R.id.account_nav:
                fragment = new AccountFragment();
                break;
        }
        return loadFragment(fragment);
    }
}