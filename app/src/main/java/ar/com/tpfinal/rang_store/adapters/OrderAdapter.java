package ar.com.tpfinal.rang_store.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.List;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final List<ItemCart> dataList;

    public OrderAdapter(List<ItemCart> dataList) { this.dataList = dataList; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(dataList != null){

            holder.setData(dataList.get(position).getProduct(),dataList.get(position).getQuantity());

            holder.itemView.setOnClickListener(view -> {
                Bundle args = new Bundle();

                Product selected = dataList.get(position).getProduct();

                Integer quantity = dataList.get(position).getQuantity();

                args.putParcelable("product", selected);
                args.putInt("quantity",quantity);
                Navigation.findNavController(view).navigate(R.id.action_buyOrderFragment_to_productInfoFragment, args);
            });
        }

    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle;
        TextView productQuantity;
        TextView productUnitPrice;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageViewOrder);
            productTitle = itemView.findViewById(R.id.titleProductOrder);
            productQuantity = itemView.findViewById(R.id.quantityOrder);
            productUnitPrice = itemView.findViewById(R.id.unitPriceOrder);
            productPrice = itemView.findViewById(R.id.priceOrder);

        }

        public void setData(Product product, Integer quantity) {
            setImage(product.getImages().get(0));
            productTitle.setText(product.getTitle());
            productQuantity.setText("Cantidad: x" + quantity);
            productUnitPrice.setText("Precio unitario: $" + product.getPrice());
            productPrice.setText("Precio total: $" + product.getPrice()*quantity);
        }

        private void setImage(String url) {
            Handler handler = new Handler();

            Runnable runnable = () -> {
                Bitmap bitmap = null;
                InputStream inputStream;
                try {
                    inputStream = new URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Bitmap finalBitmap = bitmap;
                handler.post(() -> productImage.setImageBitmap(finalBitmap));
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
