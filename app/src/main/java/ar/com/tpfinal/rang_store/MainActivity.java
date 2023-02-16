package ar.com.tpfinal.rang_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import ar.com.tpfinal.rang_store.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}