package ar.com.tpfinal.rang_store.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
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
import ar.com.tpfinal.rang_store.model.Product;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final List<Pair<Product,Integer>> dataList;

    public OrderAdapter(List<Pair<Product,Integer> > dataList) { this.dataList = dataList; }

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
            Product product = dataList.get(position).first;
            Integer quantity = dataList.get(position).second;
            holder.setData(product,quantity);
//TODO PODRIAMOS HACER QUE NAVEGUES HASTA EL INFO PRODUCT DE ESE PRODUCTO DE LA ORDEN, PERO SI JUSTO SE BORRA DE LA API RIP.
//            holder.itemView.setOnClickListener(view -> {
//                Bundle args = new Bundle();
//                Product selected = dataList.get(holder.getLayoutPosition()).first;
//                args.putParcelable("product", selected);
//                args.putInt("quantity",quantity);
//                Navigation.findNavController(view).navigate(R.id.action_buyOrderFragment_to_purchaseDataFragment, args);
//            });
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
            Log.i("PRODUCTO", "setData: "+product.getPrice());
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
