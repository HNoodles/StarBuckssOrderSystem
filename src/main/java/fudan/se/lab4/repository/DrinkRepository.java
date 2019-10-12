package fudan.se.lab4.repository;

import fudan.se.lab4.entity.Drink;

public interface DrinkRepository {
    /**
     * Get drink by name in data/drinks/country.csv
     *
     * @param nameArea  current country of language
     * @param priceArea current country of currency
     * @param name      name of drink
     * @return drink
     */
    Drink getDrink(String nameArea, String priceArea, String name, int size);

    void createDrink(Drink drink, String area);
}