package fudan.se.lab4.service.impl;

import fudan.se.lab4.dto.Ingredient;
import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.entity.IngredientEntity;
import fudan.se.lab4.repository.DrinkRepository;
import fudan.se.lab4.repository.impl.DrinkRepositoryImpl;
import fudan.se.lab4.repository.impl.IngredientRepositoryImpl;
import fudan.se.lab4.service.ProductService;

import java.util.ArrayList;
import java.util.List;


public class ProductServiceImpl implements ProductService {
    /**
     * This method should get a list of order items from order service,
     * and then transform them into a list of a Drink object.
     *
     * @param orderItems list of order items got from order service
     * @return list of drinks
     */
    @Override
    public List<Drink> orderItemsToDrinks(List<OrderItem> orderItems, String nameArea, String priceArea) {
        List<Drink> drinks = new ArrayList<>();
        DrinkRepository drinkRepository = new DrinkRepositoryImpl();

        for (OrderItem orderItem : orderItems) {
            // Transform orderItem to Drinks object
            Drink drink = drinkRepository.getDrink(nameArea, priceArea, orderItem.getName(), orderItem.getSize());

            // Transform Ingredients in orderItem to IngredientEntities
            List<IngredientEntity> ingredientEntities = ingredientToIngredientEntities(orderItem.getIngredients(), nameArea, priceArea);

            drink.setIngredientEntities(ingredientEntities);

            drinks.add(drink);
        }

        return drinks;
    }

    /**
     * This method should read information of ingredient in orderItem
     * get a map between ingredientEntity and the number of ingredientEntity and return it
     *
     * @param ingredients list of ingredients in the orderItem
     * @return a mapping from ingredientEntity and the number of it
     */
    private List<IngredientEntity> ingredientToIngredientEntities(List<Ingredient> ingredients, String nameArea, String priceArea) {
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        IngredientRepositoryImpl ingredientRepository = new IngredientRepositoryImpl();

        for (Ingredient ingredient : ingredients) {
            ingredientEntities.add(ingredientRepository.getIngredient(nameArea, priceArea, ingredient.getName()));
        }

        return ingredientEntities;
    }

}
