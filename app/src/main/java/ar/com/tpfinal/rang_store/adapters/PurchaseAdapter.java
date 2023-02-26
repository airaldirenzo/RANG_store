package ar.com.tpfinal.rang_store.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Order;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder>{

    private final List<Order> dataList;

    public PurchaseAdapter(List<Order> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Order order = dataList.get(position);
        holder.setData(order);

        holder.itemView.setOnClickListener(view -> {
            Bundle args = new Bundle();
            Order orderSelected = dataList.get(holder.getLayoutPosition());
            List<ItemCart> itemCartList = orderSelected.getItemCartList();
            args.putParcelableArrayList("cart", (ArrayList<? extends Parcelable>) itemCartList);
            args.putBoolean("readOnly",true);
            Navigation.findNavController(view).navigate(R.id.action_purchaseHistory_to_buyOrderFragment, args);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderUid;
        TextView orderDate;
        TextView orderUniqueProducts;
        TextView orderTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderUid = itemView.findViewById(R.id.purchaseId);
            orderDate = itemView.findViewById(R.id.purchaseDate);
            orderUniqueProducts = itemView.findViewById(R.id.purchaseUniqueProducts);
            orderTotalPrice = itemView.findViewById(R.id.purchasePrice);
        }

        public void setData(Order order) {

            orderUid.setText("Id de compra: " + order.getOrderUid());
            orderDate.setText("Fecha de compra: " + order.getPurchaseDate().toString());
            orderUniqueProducts.setText("Productos unicos: " + order.getUniqueProducts().toString());
            orderTotalPrice.setText("$"+order.getTotalPrice().toString());

        }

    }
}
