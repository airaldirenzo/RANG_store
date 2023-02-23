package ar.com.tpfinal.rang_store.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final List<Product> dataList;

    public ProductAdapter(List<Product> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = dataList.get(position);
        holder.dataSetPA(product);

        holder.itemView.setOnClickListener(view -> {
            Bundle args = new Bundle();
            Product selected = dataList.get(holder.getLayoutPosition());
            args.putParcelable("product", selected);
            Navigation.findNavController(view).navigate(R.id.action_productChartFragment_to_productInfoFragment, args);
        });
    }

    //TODO
    public void addProdcuts(List<Product> newProducts) {
        this.dataList.addAll(newProducts);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
        }

        public void dataSetPA(Product product) {
            setImage(product.getImages().get(0));
            productTitle.setText(product.getTitle());
            productPrice.setText("$" + product.getPrice());

        }

        private void setImage(String url) {
            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    InputStream inputStream = null;
                    try {
                        inputStream = new URL(url).openStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Bitmap finalBitmap = bitmap;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            productImage.setImageBitmap(finalBitmap);
                        }
                    });
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
