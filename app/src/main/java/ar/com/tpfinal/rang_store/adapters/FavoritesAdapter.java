package ar.com.tpfinal.rang_store.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.data.datasource.firebase.ProductMapper;
import ar.com.tpfinal.rang_store.model.Product;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{
    private List<Product> dataList ;

    public FavoritesAdapter(List<Product> dataList) { this.dataList = dataList; }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FavoritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {

        if(dataList != null){

            Product product = dataList.get(position);
            holder.setData(product);

            holder.itemView.setOnClickListener(view -> {
                Bundle args = new Bundle();

                Product selected = dataList.get(position);

                args.putParcelable("product", selected);
                Navigation.findNavController(view).navigate(R.id.action_favoritesFragment_to_productInfoFragment, args);
            });
        }

    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle;
        TextView productDescription;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageViewFavorite);
            productTitle = itemView.findViewById(R.id.titleProductFavorite);
            productDescription = itemView.findViewById(R.id.descriptionFavorite);
            productPrice = itemView.findViewById(R.id.priceFavorite);
        }

        public void setData(Product product) {
            setImage(product.getImages().get(0));
            productTitle.setText(product.getTitle());
            productDescription.setText(product.getDescription());
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
