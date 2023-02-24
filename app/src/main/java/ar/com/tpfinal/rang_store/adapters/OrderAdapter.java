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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.data.datasource.firebase.ProductMapper;
import ar.com.tpfinal.rang_store.fragments.LogIn;
import ar.com.tpfinal.rang_store.model.Category;
import ar.com.tpfinal.rang_store.model.Product;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Map<String,Object> > dataList;

    public OrderAdapter(List<Map<String,Object>> dataList) { this.dataList = dataList; }

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

            if(dataList.get(position).get("product") instanceof Product){
                holder.setData(
                        (Product) dataList.get(position).get("product"),
                        (Integer) dataList.get(position).get("quantity"));
            }
            else {
                Product product = ProductMapper.entityProductToProduct((HashMap) dataList.get(position).get("product"));
                Integer quantity = (int)(long) dataList.get(position).get("quantity");
                holder.setData(product,quantity);
            }

            //TODO PODRIAMOS HACER QUE NAVEGUES HASTA EL INFO PRODUCT DE ESE PRODUCTO DE LA ORDEN, PERO SI JUSTO SE BORRA DE LA API RIP.
            holder.itemView.setOnClickListener(view -> {
                Bundle args = new Bundle();
                Log.i("LO QUE SEA", (dataList.get(holder.getLayoutPosition()).get("product")).getClass().toString());

                Product selected = (dataList.get(position).get("product") instanceof Product) ?
                        (Product) dataList.get(position).get("product") :
                        ProductMapper.entityProductToProduct((HashMap) dataList.get(position).get("product"));

                Integer quantity = (int)(long) dataList.get(holder.getLayoutPosition()).get("quantity");
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
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageViewOrder);
            productTitle = itemView.findViewById(R.id.titleProductOrder);
            productQuantity = itemView.findViewById(R.id.quantityOrder);
            productPrice = itemView.findViewById(R.id.priceOrder);

        }

        public void setData(Product product, Integer quantity) {
            setImage(product.getImages().get(0));
            productTitle.setText(product.getTitle());
            productQuantity.setText("Cantidad: x" + quantity);
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
