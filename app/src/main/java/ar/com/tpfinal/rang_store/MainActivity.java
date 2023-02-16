package ar.com.tpfinal.rang_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

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

        NavController navHost = Navigation.findNavController(binding.getRoot());

//        NavigationView navigationView = this.findViewById(R.id.navigationView);
//        navigationView.getMenu().getItem(6).setOnMenuItemClickListener(menuItem -> {
//            switch (menuItem.getItemId()) {
//
//                case R.id.drawerHome:
//                    //TODO
//                    break;
//                case R.id.drawerSearch:
//                    //TODO
//                    break;
//                case R.id.drawerNotifications:
//                    //TODO
//                    break;
//                case R.id.drawerPurchases:
//                    //TODO
//                    break;
//                case R.id.drawerFavourites:
//                    //TODO
//                    break;
//                case R.id.drawerMyAccount:
//                    //TODO
//                    break;
//                case R.id.drawerLogOut:
//                    FirebaseAuth.getInstance().signOut();
//                    navHost.navigate(R.id.action_global_logIn);
//                    //TODO CERRAR EL DRAWER
//                    break;
//                default:
//                    throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
//            }
//
//            return true;
//        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            binding.drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
}