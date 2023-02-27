package ar.com.tpfinal.rang_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Cart;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Favorites;
import ar.com.tpfinal.rang_store.data.datasource.firebase.ProductMapper;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.AppRetrofit;
import ar.com.tpfinal.rang_store.data.factory.ProductRepositoryFactory;
import ar.com.tpfinal.rang_store.data.filter.FilterObject;
import ar.com.tpfinal.rang_store.data.repository.ProductRepository;
import ar.com.tpfinal.rang_store.databinding.ActivityMainBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

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
                        Toast.makeText(this,"Not implemented",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawerPurchases:
                        //TODO
                        break;
                    case R.id.drawerFavourites:
                        //TODO
                        break;
                    case R.id.drawerMyAccount:
                        Toast.makeText(this, "My account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawerLogOut:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(this,LoginActivity.class));
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