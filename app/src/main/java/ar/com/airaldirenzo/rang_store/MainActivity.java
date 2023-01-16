package ar.com.airaldirenzo.rang_store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ar.com.airaldirenzo.rang_store.databinding.ActivityMainBinding;
import ar.com.airaldirenzo.rang_store.databinding.LogInBinding;

public class MainActivity extends AppCompatActivity {

    private LogInBinding binding;
    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LogInBinding.inflate(getLayoutInflater());
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setSupportActionBar(binding.materialToolbar);
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.toolbarSearchbar:
                //TODO buscar? es necesario para que se abra el texto? podemos chequear texto + enter entonces filtrar
                break;
            case R.id.toolbarFavourite:
                //TODO abrir favoritos
                break;
            case R.id.toolbarCart:
                //TODO navegar hacia carrito
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}