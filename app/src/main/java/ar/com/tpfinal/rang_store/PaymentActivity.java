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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import ar.com.tpfinal.rang_store.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_RANG_store);
        super.onCreate(savedInstanceState);
        binding =  ActivityPaymentBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.materialToolbar);
        setContentView(binding.getRoot());

        // Obtencion del nav controller
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = NavHostFragment.findNavController(currentFragment);

        // Toggle para drawer
        new ActionBarDrawerToggle(this, binding.drawerLayout, binding.materialToolbar, 0,0);

        NavigationView navigationView = this.findViewById(R.id.navigationView);
        for (int i=0; i<navigationView.getMenu().size();i++) {
            navigationView.getMenu().getItem(i).setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {

                    case R.id.drawerHome:
                        startActivity(new Intent(this,MainActivity.class));
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

        switch (item.getItemId()) {
            case R.id.toolbarCart:
                Log.i("ESTE ES EL CARRITO: ", "CARRITO");
                break;

            case R.id.toolbarFavourite:
                Log.i("ESTOS SON LOS PRODUCTOS FAVORITOS: ", "FAVORITOS");
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