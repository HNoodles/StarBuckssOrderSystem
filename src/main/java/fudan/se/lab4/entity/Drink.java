package fudan.se.lab4.entity;

import java.util.List;
import java.util.Map;

/**
 * a super class for drink entities
 */
public class Drink {
    private String key;
    private String name;
    private String description;
    private String type;
    private double price;
    private int size;
    private double sizeCost;
    private List<IngredientEntity> ingredientEntities;

    public Drink() {

    }

    public Drink(int size, List<IngredientEntity> ingredientEntities) {
        this.size = size;
        this.ingredientEntities = ingredientEntities;
    }

    public Drink(String key, String name, String description, String type, double price, int size, double sizeCost) {
        setKey(key);
        setName(name);
        setDescription(description);
        setType(type);
        setPrice(price);
        setSize(size);
        setSizeCost(sizeCost);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSizeCost(double sizeCost) {
        this.sizeCost = sizeCost;
    }

    public double getSizeCost() {
        return sizeCost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<IngredientEntity> getIngredientEntities() {
        return ingredientEntities;
    }

    public void setIngredientEntities(List<IngredientEntity> ingredientEntities) {
        this.ingredientEntities = ingredientEntities;
    }

    public double cost() {
        return getPrice() + getSizeCost() + getIngredientCost();
    }

    /**
     * encapsulating the way of computing cost of different size for later maintenance
     *
     * @return the actual cost of a specific size
     */

    private double getIngredientCost() {
        double ingredientCost = 0.0;
        for (IngredientEntity ingredientEntity : ingredientEntities) {
            ingredientCost += ingredientEntity.getPrice();
        }

        return ingredientCost;
    }
}
