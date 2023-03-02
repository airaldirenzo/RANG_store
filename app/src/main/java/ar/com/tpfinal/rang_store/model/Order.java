package ar.com.tpfinal.rang_store.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.List;

public class Order implements Parcelable {

    private String orderUid;
    private List<ItemCart> itemCartList;
    private Integer uniqueProducts;
    private Date purchaseDate;
    private Double totalPrice;
    private String userUid;

    public Order(String orderUid, List<ItemCart> itemCartList, Integer uniqueProducts, Date purchaseDate, Double totalPrice) {
        this.orderUid = orderUid;
        this.itemCartList = itemCartList;
        this.uniqueProducts = uniqueProducts;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Order(List<ItemCart> itemCartList, Integer uniqueProducts, Date purchaseDate, Double totalPrice) {
        this.itemCartList = itemCartList;
        this.uniqueProducts = uniqueProducts;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public List<ItemCart> getItemCartList() {
        return itemCartList;
    }

    public void setItemCartList(List<ItemCart> itemCartList) {
        this.itemCartList = itemCartList;
    }
    public Integer getUniqueProducts(){
        return uniqueProducts;
    }
    public static Integer getUniqueProducts(List<ItemCart> itemCartList){
        return itemCartList.size();
    }

    public void setUniqueProducts(Integer uniqueProducts) {
        this.uniqueProducts = uniqueProducts;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public static Double getTotalPrice(List<ItemCart> itemCartList) {
        double totalPrice = 0;
        for(ItemCart itemCart : itemCartList){
            totalPrice += itemCart.getProduct().getPrice()*itemCart.getQuantity();
        }
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderUid);
        dest.writeTypedList(this.itemCartList);
        dest.writeValue(this.uniqueProducts);
        dest.writeSerializable(this.purchaseDate);
        dest.writeValue(this.totalPrice);
        dest.writeString(this.userUid);
    }

    public void readFromParcel(Parcel source) {
        this.orderUid = source.readString();
        this.itemCartList = source.createTypedArrayList(ItemCart.CREATOR);
        this.uniqueProducts = (Integer) source.readValue(Integer.class.getClassLoader());
        this.purchaseDate = (Date) source.readSerializable();
        this.totalPrice = (Double) source.readValue(Double.class.getClassLoader());
        this.userUid = source.readString();
    }

    protected Order(Parcel in) {
        this.orderUid = in.readString();
        this.itemCartList = in.createTypedArrayList(ItemCart.CREATOR);
        this.uniqueProducts = (Integer) in.readValue(Integer.class.getClassLoader());
        this.purchaseDate = (Date) in.readSerializable();
        this.totalPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.userUid = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
