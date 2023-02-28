package ar.com.tpfinal.rang_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.com.tpfinal.rang_store.data.datasource.firebase.Cart;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Favorites;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Purchase;
import ar.com.tpfinal.rang_store.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_RANG_store);
        super.onCreate(savedInstanceState);
        binding =  ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.materialToolbar);
        setContentView(binding.getRoot());

        //Obtencion de las instancias de FireAuth y Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Obtencion del nav controller
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = NavHostFragment.findNavController(currentFragment);

        // Toggle para drawer
        new ActionBarDrawerToggle(this, binding.drawerLayout, binding.materialToolbar, 0,0);

        NavigationView navigationView = this.findViewById(R.id.navigationView);
        for (int i=0; i<navigationView.getMenu().size();i++) {
            navigationView.getMenu().getItem(i).setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.drawerHome:
                        navController.navigate(R.id.action_global_productChartFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.drawerNotifications:
                        //TODO
                        Toast.makeText(this,"No implementado",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawerPurchases:
                        Purchase.goToPurchases(currentFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.drawerFavourites:
                        Favorites.goToFavorites(currentFragment);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.drawerMyAccount:
                        Toast.makeText(this, "Mi cuenta", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawerLogOut:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(this,LoginActivity.class));
                        finish();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                return true;
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarCart:
                Cart.goToCart(currentFragment);
                break;
            case R.id.toolbarFavourite:
                Favorites.goToFavorites(currentFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}