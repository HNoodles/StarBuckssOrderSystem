package fudan.se.lab4.service;

import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.entity.Drink;

import java.util.List;

public interface ProductService {
    /**
     * This method should get a list of order items from order service,
     * and then transform them into a list of a Drink object.
     *
     * @param orderItems list of order items got from order service
     * @param nameArea   current country of language
     * @param priceArea  current country of currency
     * @return list of drinks
     */
    List<Drink> orderItemsToDrinks(List<OrderItem> orderItems, String nameArea, String priceArea);
}
