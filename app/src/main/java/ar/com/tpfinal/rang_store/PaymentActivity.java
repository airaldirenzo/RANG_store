package ar.com.tpfinal.rang_store;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.tpfinal.rang_store.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_RANG_store);
        super.onCreate(savedInstanceState);
        binding =  ActivityPaymentBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.materialToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}