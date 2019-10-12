package fudan.se.lab4.strategy.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.strategy.Strategy;

import java.text.MessageFormat;
import java.util.List;

public class CoffeeTeaStrategy extends Strategy {
    public CoffeeTeaStrategy() {
        type = "CombineDiscount";
    }

    /**
     * this method determines for a specific strategy, how much
     * an order cost and return
     *
     * @param drinks        the drinks in the order to analyze
     * @param originalPrice the original total price of the drinks
     * @return discount of applying the strategy
     */
    @Override
    public double cost(List<Drink> drinks, Double originalPrice, String pattern) {
        boolean haveTea = false, haveCoffee = false;
        for (Drink drink : drinks) {
            if (drink.getType().equals("tea")) {
                haveTea = true;
            } else if (drink.getType().equals("coffee")) {
                haveCoffee = true;
            }

            if (haveCoffee && haveTea) {
                msgs.add(MessageFormat.format(InfoConstant.getInfo("COFFEE_TEA_DISCOUNT"), pattern + roundHalfUp(originalPrice * 0.15)));
                logMsgs.add(MessageFormat.format(InfoConstant.getLogInfo("COFFEE_TEA_DISCOUNT"), pattern + roundHalfUp(originalPrice * 0.15)));
                return roundHalfUp(originalPrice * 0.15);
            }
        }

        return 0.0;
    }
}
