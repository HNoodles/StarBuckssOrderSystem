package fudan.se.lab4.repository.impl;

import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.repository.DrinkRepository;
import fudan.se.lab4.util.DataUtil;

public class DrinkRepositoryImpl implements DrinkRepository {

    @Override
    public Drink getDrink(String nameArea, String priceArea, String name, int size) {

        String[] informationByName = DataUtil.readByNameInTable("drinks/" + nameArea, name);

        double price = DataUtil.getPriceInCurrencyPrices(informationByName[4], priceArea);
        double sizePrice = DataUtil.getPriceInCurrencyPrices(informationByName[4 + size], priceArea);

        return new Drink(informationByName[0], informationByName[1], informationByName[2], informationByName[3],
                price, size, sizePrice);
    }

    @Override
    public void createDrink(Drink drink, String country) {

    }
}
