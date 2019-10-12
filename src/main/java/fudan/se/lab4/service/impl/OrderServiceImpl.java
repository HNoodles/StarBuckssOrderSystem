package fudan.se.lab4.service.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.dto.Order;
import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.dto.PaymentInfo;
import fudan.se.lab4.service.OrderService;
import fudan.se.lab4.service.PriceService;
import fudan.se.lab4.service.ProductService;
import fudan.se.lab4.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import fudan.se.lab4.entity.*;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private ProductService productService = new ProductServiceImpl();
    private PriceService priceService = new PriceServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * This method should get an order from input
     * transform it into a list of drinks by calling orderItemsToDrinks in ProductServiceImpl
     * the get the original price of drinks
     * store it in a PaymentInfo object and return
     *
     * @param order      object of Order from front
     * @param country    country of client being
     * @param currency   using currency info, country and label. 0:country/area, 1:pattern of currency
     * @param strategies strategies can be used in calculating cost
     * @return PaymentInfo object with original price set
     */
    @Override
    public PaymentInfo pay(Order order, String country, String[] currency, List<Strategy> strategies) {
        List<String> msgs = new ArrayList<>();
        List<String> logMsgs = new ArrayList<>();
        List<Drink> drinks = productService.orderItemsToDrinks(order.getOrderItems(), country, currency[0]); // currency[0] : country of the currency

        double originPrice = priceService.getPriceByDrinks(drinks);
        double discount = priceService.getDiscountByOrder(drinks, msgs, logMsgs, originPrice, strategies, currency[1]); // currency[1] : pattern of the currency

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPrice(originPrice);
        paymentInfo.setDiscount(Math.round((discount) * 100) / 100.0);
        paymentInfo.setDiscountPrice(originPrice - discount);
        paymentInfo.setMsgs(msgs);

        StringBuilder goodsLog = new StringBuilder();
        goodsLog.append(MessageFormat.format(InfoConstant.getLogInfo("LOG_ORDER_ID"), order.getId())).append("\n");
        for (Drink drink : drinks) {
            StringBuilder goodsInfo = new StringBuilder();
            goodsLog.append(drink.getKey()).append(": ").append(MessageFormat.format(InfoConstant.getLogInfo("LOG_SIZE"), drink.getSize()));
            goodsInfo.append(drink.getName()).append(": ").append(MessageFormat.format(InfoConstant.getInfo("LOG_SIZE"), drink.getSize()));

            if (drink.getIngredientEntities().size() > 0) {
                goodsLog.append(" ").append(InfoConstant.getLogInfo("LOG_INGREDIENT"));
                goodsInfo.append(" ").append(InfoConstant.getInfo("LOG_INGREDIENT"));

                for (IngredientEntity ingredient : drink.getIngredientEntities()) {
                    goodsLog.append(ingredient.getKey()).append(",");
                    goodsInfo.append(ingredient.getName()).append(",");
                }
                goodsLog.deleteCharAt(goodsLog.length() - 1);
                goodsInfo.deleteCharAt(goodsInfo.length() - 1);
            }
            goodsLog.append(" ").append(MessageFormat.format(InfoConstant.getLogInfo("LOG_ORIGINAL_PRICE"), drink.cost())).append("\n");
            goodsInfo.append(" ").append(MessageFormat.format(InfoConstant.getInfo("LOG_ORIGINAL_PRICE"), drink.cost()));

            paymentInfo.addGoodsInfo(goodsInfo.toString());
        }
        goodsLog.append(InfoConstant.getLogInfo("LOG_STRATEGY_USED"));
        for (String str : logMsgs) { // record the strategies
            goodsLog.append(" ").append(str);
        }

        goodsLog.append("\n").append(MessageFormat.format(InfoConstant.getLogInfo("LOG_ORIGINAL_TOTAL_PRICE"), currency[1] + originPrice));
        goodsLog.append("\n").append(MessageFormat.format(InfoConstant.getLogInfo("LOG_DISCOUNTED_PRICE"), currency[1] + originPrice));
        goodsLog.append("\n");
        logger.warn(goodsLog.toString());

        return paymentInfo;
    }

    /**
     * This method create an order from the got order item list and
     * set the order id to a system-time-corresponding string
     *
     * @param orderItems list of order items in the order
     * @return the created order
     */
    @Override
    public Order createOrder(List<OrderItem> orderItems) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String id = dateFormat.format(new Date()) + (int) (Math.random() * 100);
        return new Order(id, orderItems);
    }
}
