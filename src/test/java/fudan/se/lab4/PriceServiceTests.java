package fudan.se.lab4;

import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.service.PriceService;
import fudan.se.lab4.service.impl.PriceServiceImpl;
import fudan.se.lab4.strategy.Strategy;
import fudan.se.lab4.strategy.impl.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Based on the barricade mechanism,
 * we do not consider dirty tests here,
 * so there will not expected thrown exception
 */
public class PriceServiceTests {
    private PriceService priceService;
    private List<Drink> drinks;
    private List<Strategy> strategies;
    private List<String> msgs;
    private List<String> logmMsgs;

    /**
     * set up global variables
     */
    @Before
    public void setup() {
        priceService = new PriceServiceImpl();
        drinks = new ArrayList<>();
        msgs = new ArrayList<>();
        strategies = new ArrayList<>();
        logmMsgs = new ArrayList<>();
    }

    /**
     * set global variables to null after testing
     */
    @After
    public void tearDown() {
        priceService = null;
        drinks = null;
        msgs = null;
        strategies = null;
        logmMsgs = null;
    }

    @Test
    public void testClassifyStrategy() {
        List<Strategy> strategies = new ArrayList<>();
        strategies.add(new DoubleElevenStrategy());
        strategies.add(new MoneyOffStrategy());
        strategies.add(new CoffeeTeaStrategy());
        strategies.add(new ThreeTeaStrategy());
        strategies.add(new TwoCappuccionStrategy());
        strategies.add(new TwoBigEspressoStrategy());

        Map<String, List<Strategy>> map = priceService.getClassifiedStrategies(strategies);
        assertEquals("Wrong strategy classification 1!", 3, map.size());

        strategies.remove(4);
        map = priceService.getClassifiedStrategies(strategies);
        assertEquals("Wrong strategy classification 2!", 3, map.size());

        strategies.remove(1);
        map = priceService.getClassifiedStrategies(strategies);
        assertEquals("Wrong strategy classification 3!", 2, map.size());

        strategies.remove(0);
        map = priceService.getClassifiedStrategies(strategies);
        assertEquals("Wrong strategy classification 4!", 1, map.size());
    }

    @Test
    public void testCoffeeTeaStrategy() {
        strategies.add(new CoffeeTeaStrategy());
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 3, 2, 1));
        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 2.5, 1, 0.5));
        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,7, strategies, "$");

        assertTrue(Math.abs((7 - discount) - 5.95) < 0.01);
    }

    @Test
    public void testMoneyOffStrategy() {
        strategies.add(new MoneyOffStrategy());
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10)); // 这里价格时假设的
        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 90, 1, 10));
        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,160, strategies, "$");
        assertTrue(Math.abs(discount - 30) < 0.01);
    }

    @Test
    public void testCombineDiscountStrategy1() {
        strategies.add(new TwoBigEspressoStrategy());
        drinks.add(new Drink("espresso", "espresso", "tasty coffee!", "coffee", 50, 3, 10)); // 这里价格时假设的
        drinks.add(new Drink("espresso", "espresso", "tasty coffee!", "coffee", 50, 3, 10));
        drinks.add(new Drink("espresso", "espresso", "tasty coffee!", "coffee", 50, 3, 10));

        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,180, strategies, "$");
        assertTrue(Math.abs(discount - 20) < 0.01);
    }

    @Test
    public void testCombineDiscountStrategy2() {
        strategies.add(new TwoCappuccionStrategy());
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10));
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10));
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10));
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10));
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10));

        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,300, strategies, "$");
        assertTrue(Math.abs(discount - 50) < 0.01);
    }

    @Test
    public void testCombineDiscountStrategy3() {
        strategies.add(new ThreeTeaStrategy());
        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 40, 1, 10));
        drinks.add(new Drink("red tea", "red tea", "tasty tea!", "tea", 50, 1, 10));
        drinks.add(new Drink("red tea", "red tea", "tasty tea!", "tea", 50, 1, 10));

        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,270, strategies, "$");
        assertTrue(Math.abs(discount - 50) < 0.01);
    }

    @Test
    public void testMutiStrategy() {
        strategies.add(new MoneyOffStrategy());
        strategies.add(new CoffeeTeaStrategy());

        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "green tea", "tasty tea!", "tea", 40, 1, 10));
        drinks.add(new Drink("cappuccino", "cappuccino", "tasty coffee!", "coffee", 50, 2, 10));

        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,160, strategies, "$");
        assertTrue(Math.abs(discount - 30) < 0.01);
    }

    @Test
    public void testCombinedDiscount2() {
        strategies.add(new ThreeTeaStrategy());
        strategies.add(new TwoCappuccionStrategy());
        strategies.add(new TwoBigEspressoStrategy());

        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));

        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 50, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("herbal tea", "凉茶", "不好喝的茶", "tea", 40, 2, 20));

        drinks.add(new Drink("cappuccino", "卡布奇诺", "好喝的咖啡！", "coffee", 50, 2, 10));

        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,480, strategies, "￥");

        assertTrue(Math.abs(discount - 50) < 0.01);

    }

    @Test
    public void testCombinedDiscount3() {
        strategies.add(new ThreeTeaStrategy());
        strategies.add(new TwoCappuccionStrategy());
        strategies.add(new TwoBigEspressoStrategy());
        strategies.add(new CoffeeTeaStrategy());

        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));

        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 50, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("green tea", "绿茶", "好喝的茶！", "tea", 40, 1, 10));
        drinks.add(new Drink("herbal tea", "凉茶", "不好喝的茶", "tea", 40, 2, 20));

        drinks.add(new Drink("cappuccino", "卡布奇诺", "好喝的咖啡！", "coffee", 50, 2, 10));

        double discount = priceService.getDiscountByOrder(drinks, msgs, logmMsgs,480, strategies, "￥");

        assertTrue(Math.abs(discount - 122) < 0.01);

    }
}
