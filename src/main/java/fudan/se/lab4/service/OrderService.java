package fudan.se.lab4.service;

import fudan.se.lab4.dto.Order;
import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.dto.PaymentInfo;
import fudan.se.lab4.strategy.Strategy;

import java.util.List;

public interface OrderService {
    /**
     * This method should deal with the price and discount of the order.
     * It should first call methods of ProductService to get list of drinks.
     * Then, it should call methods of PriceService to get attributes needed
     * by PaymentInfo and then set them.
     *
     * @param order      object of Order from front
     * @param country    country of client being
     * @param currency   using currency info, country and label
     * @param strategies strategies can be used in calculating cost
     * @return generated paymentInfo
     */
    PaymentInfo pay(Order order, String country, String[] currency, List<Strategy> strategies);

    /**
     * This method create an order from the got order item list and
     * set the order id to a system-time-corresponding string
     *
     * @param orderItems list of order items in the order
     * @return the created order
     */
    Order createOrder(List<OrderItem> orderItems);
}
