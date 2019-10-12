package fudan.se.lab4.repository.impl;

import fudan.se.lab4.entity.IngredientEntity;
import fudan.se.lab4.repository.IngredientRepository;
import fudan.se.lab4.util.DataUtil;

public class IngredientRepositoryImpl implements IngredientRepository {
    /**
     * create ingredientEntity with given arears
     *
     * @param nameArea  current country of language
     * @param priceArea current country of currency
     * @param name      name of ingredient
     * @return ingredient entity
     */
    @Override
    public IngredientEntity getIngredient(String nameArea, String priceArea, String name) {
        String[] informationByName = DataUtil.readByNameInTable("ingredients/" + nameArea, name);

        double price = DataUtil.getPriceInCurrencyPrices(informationByName[2], priceArea);

        return new IngredientEntity(informationByName[0], informationByName[1], price);
    }

    @Override
    public void createIngredient(IngredientEntity ingredient, String country) {

    }

}
