package ar.com.tpfinal.rang_store.data.datasource.firebase;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Order;

public class OrderMapper {

    public static Order entityOrderToOrder(HashMap data,String orderUid){

        List<ItemCart> itemCartList = (List<ItemCart>) ItemCartMapper.entityListItemCartToListItemCart((List<HashMap>) data.get("itemCartList"));
        Integer uniqueProducts = Order.getUniqueProducts(itemCartList);
        Date date = ((Timestamp) data.get("purchaseDate")).toDate();
        double totalPrice = Order.getTotalPrice(itemCartList);

        Order order = new Order(orderUid,itemCartList,uniqueProducts,date,totalPrice);

        return order;
    }
}
