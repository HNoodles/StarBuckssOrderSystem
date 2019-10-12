package fudan.se.lab4;

import fudan.se.lab4.constant.DataConstant;
import fudan.se.lab4.constant.EntityConstant;
import fudan.se.lab4.dto.Ingredient;
import fudan.se.lab4.dto.Order;
import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.dto.PaymentInfo;
import fudan.se.lab4.service.ClientService;
import fudan.se.lab4.service.OrderService;
import fudan.se.lab4.service.impl.ClientServiceImpl;
import fudan.se.lab4.service.impl.OrderServiceImpl;
import fudan.se.lab4.strategy.Strategy;
import fudan.se.lab4.strategy.impl.CoffeeTeaStrategy;
import fudan.se.lab4.strategy.impl.DoubleElevenStrategy;
import fudan.se.lab4.strategy.impl.MoneyOffStrategy;
import fudan.se.lab4.strategy.impl.ThreeTeaStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Based on the barricade mechanism,
 * we do not consider dirty tests here,
 * so there will not expected thrown exception
 */
public class OrderServiceTests {
    private ClientService clientService;
    private OrderService orderService;
    private Order order;

    /**
     * set up global variables
     */
    @Before
    public void setup() {
        clientService = new ClientServiceImpl();
        orderService = new OrderServiceImpl();
        order = new Order();
    }

    /**
     * destroy global variables after test
     */
    @After
    public void tearDown() {
        clientService = null;
        orderService = null;
        order = null;
    }

    /**
     * a test of correct order,
     * the more complicated cases will be tested in PriceServiceTests
     */
    @Test
    public void testCorrectOrder() {
        // strategies
        String country = DataConstant.HK_NAME;
        String[] currency = {DataConstant.CHINA_Name, DataConstant.CHINA_CURRENCY};
        List<Strategy> strategies = new ArrayList<>();
        strategies.add(new ThreeTeaStrategy());
        strategies.add(new DoubleElevenStrategy());

        // get ingredient list for red tea
        List<Ingredient> ingredients1 = new ArrayList<>();   // 2.2￥
        ingredients1.add(new Ingredient(EntityConstant.MILK_NAME)); // 1.2￥
        ingredients1.add(new Ingredient(EntityConstant.SUGAR_NAME)); // 1￥

        // get ingredient list for green tea
        List<Ingredient> ingredients2 = new ArrayList<>();  // 2.2￥
        ingredients2.add(new Ingredient(EntityConstant.CHOC0LATE_NAME)); // 1.2￥
        ingredients2.add(new Ingredient(EntityConstant.CREAM_NAME)); // 1￥

        // generate order items
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(EntityConstant.RED_TEA_NAME, ingredients1, 1)); // 22.2￥
        orderItems.add(new OrderItem(EntityConstant.GREEN_TEA_NAME, ingredients2, 2)); // 22.2￥
        orderItems.add(new OrderItem(EntityConstant.GREEN_TEA_NAME, ingredients1, 2)); // 22.2￥
        orderItems.add(new OrderItem(EntityConstant.GREEN_TEA_NAME, ingredients2, 3)); // 23.2￥

        // generate order
        order.setOrderItems(orderItems); // should have one red tea off, 71.8￥, -18￥

        // test
        clientService.setCountry(country);
        clientService.setLocaleByCountry();
        clientService.setCurrency(currency[0]);

        PaymentInfo actual = orderService.pay(order, country, currency, strategies);

        // generate expected paymentInfo
        List<String> expectedMsgs = new ArrayList<>();
        expectedMsgs.add(MessageFormat.format("Three cups of tea with another for free: Saving {0}", "RMB￥18.0"));
        PaymentInfo expected = new PaymentInfo(89.8, 18.0, 71.8, expectedMsgs);

        // compare two paymentInfo: actual and expected
        assertTrue("Wrong price!",
                Math.abs(expected.getPrice() - actual.getPrice()) < 0.01);
        assertTrue("Wrong discount!",
                Math.abs(expected.getDiscount() - actual.getDiscount()) < 0.01);
        assertTrue("Wrong discountPrice!",
                Math.abs(expected.getDiscountPrice() - actual.getDiscountPrice()) < 0.01);

        for (int i = 0; i < expectedMsgs.size(); i++) {
            assertEquals("Wrong message!", expected.getMsgs().get(i), actual.getMsgs().get(i));
        }

        // another test
        strategies.add(new DoubleElevenStrategy());
        strategies.add(new MoneyOffStrategy());
        strategies.add(new CoffeeTeaStrategy());
        strategies.remove(0);

        orderItems.add(new OrderItem(EntityConstant.CAPPUCCINO_NAME, ingredients1, 3)); // 30.2￥
        orderItems.add(new OrderItem(EntityConstant.CAPPUCCINO_NAME, ingredients1, 2)); // 28.2￥
        order.setOrderItems(orderItems); // 148.2￥ CombineDiscountStrategy -￥29.0 MoneyOffStrategy -￥30.0 CoffeeTeaStrategy -￥22.23

        PaymentInfo actual2 = orderService.pay(order, country, currency, strategies);

        // generate expected paymentInfo
        List<String> expectedMsgs2 = new ArrayList<>();
        expectedMsgs2.add(MessageFormat.format("Saving 30 for every 100: Saving {0}", "RMB￥30.0"));
        PaymentInfo expected2 = new PaymentInfo(148.2, 30.0, 118.2, expectedMsgs2);

        // compare two paymentInfo: actual and expected
        assertTrue("Wrong price!",
                Math.abs(expected2.getPrice() - actual2.getPrice()) < 0.01);
        assertTrue("Wrong discount!",
                Math.abs(expected2.getDiscount() - actual2.getDiscount()) < 0.01);
        assertTrue("Wrong discountPrice!",
                Math.abs(expected2.getDiscountPrice() - actual2.getDiscountPrice()) < 0.01);

        for (int i = 0; i < expectedMsgs2.size(); i++) {
            assertEquals("Wrong message!", expected2.getMsgs().get(i), actual2.getMsgs().get(i));
        }

    }
}
