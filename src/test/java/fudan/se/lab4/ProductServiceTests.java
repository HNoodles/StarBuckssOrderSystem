package fudan.se.lab4;

import fudan.se.lab4.constant.EntityConstant;
import fudan.se.lab4.dto.Ingredient;
import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.entity.IngredientEntity;
import fudan.se.lab4.service.ProductService;
import fudan.se.lab4.service.impl.ProductServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Based on the barricade mechanism,
 * we do not consider dirty tests here,
 * so there will not expected thrown exception
 */
public class ProductServiceTests {
    private ProductService productService;

    /**
     * set up global variables
     */
    @Before
    public void setup() {
        productService = new ProductServiceImpl();
    }

    /**
     * set global variables to null after tests
     */
    @After
    public void tearDown() {
        productService = null;
    }


    /**
     * a test for correctly transforming order items to drink list
     */
    @Test
    public void testCorrectOrderItemsToDrinks() {
        List<OrderItem> orderItems = new ArrayList<>();

        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("milk"));
        ingredients1.add(new Ingredient("sugar"));
        OrderItem orderItem1 = new OrderItem("cappuccino", ingredients1, 2);
        orderItems.add(orderItem1);

        List<Drink> drinks = productService.orderItemsToDrinks(orderItems, "United States", "United States");

        Drink drink0 = drinks.get(0);
        assertEquals("cappuccino", drink0.getName()); // name should be cappuccino
        assertTrue(Math.abs(drink0.getPrice() - 3) < 0.01); // price should be 3
        assertEquals(2, drink0.getSize()); // size should be 2
        assertEquals("milk", drink0.getIngredientEntities().get(0).getName());
        assertEquals("sugar", drink0.getIngredientEntities().get(1).getName());
        assertEquals("tasty coffee!", drink0.getDescription());
        assertEquals("coffee", drink0.getType());


        List<OrderItem> orderItems2 = new ArrayList<>();
        List<Ingredient> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredient("milk"));
        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("cream"));
        ingredients2.add(new Ingredient("sugar"));
        OrderItem orderItem2 = new OrderItem("espresso", ingredients3, 1); // 20
        OrderItem orderItem3 = new OrderItem("fruit tea", ingredients2, 3); // 25
        orderItems2.add(orderItem2);
        orderItems2.add(orderItem3);

        List<Drink> drinks2 = productService.orderItemsToDrinks(orderItems2, "Hong Kong", "中国");
        Drink drink1 = drinks2.get(0);
        Drink drink2 = drinks2.get(1);
        assertTrue(Math.abs(drink1.getPrice() - 20) < 0.01);
        assertEquals(3, drink2.getSize());
        assertEquals("milk", drink1.getIngredientEntities().get(0).getName());
        assertEquals("sugar", drink2.getIngredientEntities().get(1).getName());
    }

}
