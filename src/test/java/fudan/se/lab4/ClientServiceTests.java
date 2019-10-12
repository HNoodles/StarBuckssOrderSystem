package fudan.se.lab4;

import fudan.se.lab4.service.ClientService;
import fudan.se.lab4.service.impl.ClientServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientServiceTests {
    private ClientService clientService;

    /**
     * set up global variables
     */
    @Before
    public void setup() {
        clientService = new ClientServiceImpl();
    }

    /**
     * destroy global variables after test
     */
    @After
    public void tearDown() {
        clientService = null;
    }

    /**
     * test the initialization of country lists
     */
    @Test
    public void testGetCountryList() {
        List<String> countryList = clientService.getCountryList();

        List<String> expectedCountryList = new ArrayList<>();
        expectedCountryList.add("United States");
        expectedCountryList.add("中国");
        expectedCountryList.add("Hong Kong");

        assertEquals("Wrong ingredient list size!", countryList.size(), expectedCountryList.size());

        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals("Wrong country list!", countryList.get(i), expectedCountryList.get(i));
        }

    }

    /**
     * test the initialization of currency lists when country is set
     */
    @Test
    public void testGetCurrencyList() {
        clientService.setCountry("Hong Kong");
        List<String> currencyList = clientService.getCurrencyList();

        List<String> expectedCurrencyList = new ArrayList<>();
        expectedCurrencyList.add("中国");
        expectedCurrencyList.add("Hong Kong");

        assertEquals("Wrong currency list size!", currencyList.size(), expectedCurrencyList.size());

        for (int i = 0; i < expectedCurrencyList.size(); i++) {
            assertEquals("Wrong currency list!", currencyList.get(i), expectedCurrencyList.get(i));
        }
    }

    /**
     * test the initialization of drink lists when country is set
     */
    @Test
    public void testGetDrinkList() {
        clientService.setCountry("中国");
        List<String> drinkList = clientService.getDrinkList();

        List<String> expectedDrinkList = new ArrayList<>();
        expectedDrinkList.add("卡布基诺");
        expectedDrinkList.add("意式浓缩咖啡");
        expectedDrinkList.add("绿茶");
        expectedDrinkList.add("红茶");
        expectedDrinkList.add("奶茶");
        expectedDrinkList.add("凉茶");

        assertEquals("Wrong drink list size!", drinkList.size(), expectedDrinkList.size());

        for (int i = 0; i < expectedDrinkList.size(); i++) {
            assertEquals("Wrong drink list!", drinkList.get(i), expectedDrinkList.get(i));
        }
    }

    /**
     * test the initialization of ingredient lists when country is set
     */
    @Test
    public void testGetIngredientList() {
        clientService.setCountry("中国");
        List<String> ingredientList = clientService.getIngredientList();

        List<String> expectedIngredientList = new ArrayList<>();
        expectedIngredientList.add("牛奶");
        expectedIngredientList.add("巧克力");
        expectedIngredientList.add("奶油");
        expectedIngredientList.add("糖");

        assertEquals("Wrong ingredient list size!", ingredientList.size(), expectedIngredientList.size());

        for (int i = 0; i < expectedIngredientList.size(); i++) {
            assertEquals("Wrong ingredient list!", ingredientList.get(i), expectedIngredientList.get(i));
        }
    }

    /**
     * test the initialization of strategy lists when country is set
     */
    @Test
    public void testGetStrategyList() {
        clientService.setCountry("中国");
        clientService.setLocaleByCountry();
        List<String> strategyList = clientService.getStrategyList();

        List<String> expectedStrategyList = new ArrayList<>();
        expectedStrategyList.add("双十一优惠");
        expectedStrategyList.add("咖啡+茶优惠");
        expectedStrategyList.add("所有商品满100省30");
        expectedStrategyList.add("茶类买3送1");
        expectedStrategyList.add("大杯浓缩咖啡2杯8折");
        expectedStrategyList.add("卡布奇诺第2杯半价");

        assertEquals("Wrong strategy list size!", strategyList.size(), expectedStrategyList.size());

        for (int i = 0; i < expectedStrategyList.size(); i++) {
            assertEquals("Wrong strategy list!", strategyList.get(i), expectedStrategyList.get(i));
        }
    }

    /**
     * test the add, delete and set of strategies
     */
    @Test
    public void testSetStrategy() {
        clientService.setCountry("中国");
        clientService.setLocaleByCountry();
        List<String> strategyList = new ArrayList<>();
        strategyList.add("DoubleElevenStrategy");
        strategyList.add("CoffeeTeaStrategy");
        clientService.addStrategy(strategyList);

        List<String> expectedStrategyList = new ArrayList<>();
        expectedStrategyList.add("DoubleElevenStrategy");
        expectedStrategyList.add("CoffeeTeaStrategy");

        assertEquals("Wrong strategy size!", expectedStrategyList.size(), clientService.getStrategies().size());

        String path = "fudan.se.lab4.strategy.impl.";
        for (int i = 0; i < expectedStrategyList.size(); i++) {
            assertEquals("Wrong set strategy!", path + expectedStrategyList.get(i), clientService.getStrategies().get(i).getClass().getName());
        }

    }
}
