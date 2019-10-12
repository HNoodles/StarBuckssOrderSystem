package fudan.se.lab4.entity;

/**
 * a super class for ingredient entities
 */
public class IngredientEntity {
    private String key;
    private String name;
    private double price;

    public IngredientEntity(String key, String name, double price) {
        this.key = key;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
