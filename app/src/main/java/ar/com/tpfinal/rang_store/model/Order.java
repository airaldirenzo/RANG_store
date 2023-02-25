package ar.com.tpfinal.rang_store.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private List<ItemCart> itemCartList;
    private String userUid;
    private LocalDateTime purchaseDateTime;

    public Order(List<ItemCart> itemCartList, String userUid, LocalDateTime purchaseDateTime) {
        this.itemCartList = itemCartList;
        this.userUid = userUid;
        this.purchaseDateTime = purchaseDateTime;
    }

    public List<ItemCart> getItemCartList() {
        return itemCartList;
    }

    public void setItemCartList(List<ItemCart> itemCartList) {
        this.itemCartList = itemCartList;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(LocalDateTime purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }
}
