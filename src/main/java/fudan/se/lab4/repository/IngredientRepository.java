package fudan.se.lab4.repository;


import fudan.se.lab4.entity.IngredientEntity;

public interface IngredientRepository {

    /**
     * Get ingredient by name in data/ingredients/country.csv
     *
     * @param nameArea  current country of language
     * @param priceArea current country of currency
     * @param name      name of ingredient
     * @return ingredientEntity
     */
    IngredientEntity getIngredient(String nameArea, String priceArea, String name);

    void createIngredient(IngredientEntity ingredient, String area);
}
