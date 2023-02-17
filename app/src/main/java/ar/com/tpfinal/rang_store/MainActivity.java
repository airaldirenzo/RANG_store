package ar.com.tpfinal.rang_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.AppRetrofit;
import ar.com.tpfinal.rang_store.data.factory.ProductRepositoryFactory;
import ar.com.tpfinal.rang_store.data.repository.ProductRepository;
import ar.com.tpfinal.rang_store.databinding.ActivityMainBinding;
import ar.com.tpfinal.rang_store.model.Product;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    long tInicio = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.materialToolbar);
        setContentView(binding.getRoot());

        // Toggle para drawer
        new ActionBarDrawerToggle(this, binding.drawerLayout, binding.materialToolbar, 0,0);


        NavigationView navigationView = this.findViewById(R.id.navigationView);
        for (int i=0; i<navigationView.getMenu().size();i++) {
            navigationView.getMenu().getItem(i).setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {

                    case R.id.drawerHome:
                        //TODO
                        break;
                    case R.id.drawerSearch:
                        //TODO
                        break;
                    case R.id.drawerNotifications:
                        //TODO
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
                        //TODO CERRAR EL DRAWER
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                return true;
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        OnResult<List<Product>> callback = new OnResult<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                long tFinal = System.currentTimeMillis();
                long tDiferencia = tFinal - tInicio;
                Log.i("TIEMPO DE ESPERA", ""+ tDiferencia);

                int i = 1;
                for (Product product : result) {
                    Log.i("PRODUCTO "+ i, product.toString());
                    i++;
                }
            }
            @Override
            public void onError(Throwable exception) {
                exception.printStackTrace();
            }
        };

        ProductRepository pr = ProductRepositoryFactory.create();

        switch (item.getItemId()) {
            case R.id.toolbarCart:


                AppRetrofit.EXECUTOR_API.execute(()-> {
                    Log.i("INICIO CONSULTA", "");
                    pr.listProducts(callback);
                });

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