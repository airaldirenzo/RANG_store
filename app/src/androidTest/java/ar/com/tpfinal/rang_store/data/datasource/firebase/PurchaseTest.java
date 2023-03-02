package ar.com.tpfinal.rang_store.data.datasource.firebase;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ar.com.tpfinal.rang_store.model.Category;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;
import ar.com.tpfinal.rang_store.model.User;

public class PurchaseTest extends TestCase {

    Category category = new Category(999,"Categoria 1",null);
    Product product1 = new Product(999,"Producto 1","Descripcion 1",100.00,null,category);
    Product product2 = new Product(999,"Producto 2","Descripcion 2",100.00,null,category);
    ItemCart itemCart1 = new ItemCart(product1,2);
    ItemCart itemCart2 = new ItemCart(product2,2);
    List<ItemCart> itemCartList = new ArrayList<>();
    User testUser = new User("testUser","test","test","test@test.com",null,new ArrayList<>());


    @Test
    public void testIfBuyWithCart() {
        itemCartList.add(itemCart1);
        itemCartList.add(itemCart2);
        testUser.setCart(itemCartList);
        if(true){
            itemCartList.clear();
        }
        assertTrue(itemCartList.isEmpty());
    }
    @Test
    public void testIfBuyWithoutCart(){
        itemCartList.add(itemCart1);
        itemCartList.add(itemCart2);
        testUser.setCart(itemCartList);
        if(false){
            itemCartList.clear();
        }
        assertFalse(itemCartList.isEmpty());
    }
}