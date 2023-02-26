package ar.com.tpfinal.rang_store.data.datasource.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class ItemCartMapper {

    public static List<ItemCart> entityListItemCartToListItemCart(List<HashMap> data){

        List<ItemCart> itemCartList = new ArrayList<>();

        for(int i = 0; i < data.size(); i++){

            Product product = ProductMapper.entityProductToProduct((HashMap) data.get(i).get("product"));
            Integer quantity = (int)(long) data.get(i).get("quantity");

            ItemCart itemCart = new ItemCart(product,quantity);
            itemCartList.add(itemCart);

        }

        return itemCartList;
    }

}
