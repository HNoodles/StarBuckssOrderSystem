package fudan.se.lab4.strategy.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.strategy.Strategy;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ThreeTeaStrategy extends Strategy {
    public ThreeTeaStrategy() {
        type = "CombineDiscount";
    }

    public double cost(List<Drink> drinks, Double originalPrice, String pattern) {
        List<Drink> teas = new ArrayList<>();
        double discount = 0;

        for (Drink drink : drinks) {
            if (drink.getKey().equals("green tea") || drink.getKey().equals("red tea"))
                teas.add(drink);
        }

        if (teas.size() >= 4) {
            // Sort by price in descending order
            teas.sort((Drink o1, Drink o2) -> {
                        if (o1.getPrice() == o2.getPrice())
                            return 0;
                        else
                            return o1.getPrice() > o2.getPrice() ? -1 : 1;
                    }
            );

            // For every four cups of tea, subtract the base price of the most expensive tea
            int count = teas.size() / 4;

            for (int i = 0; i < count; i++) {
                discount += teas.get(i).getPrice();
            }
            discount = roundHalfUp(discount);
            msgs.add(MessageFormat.format(InfoConstant.getInfo("TEA_DISCOUNT"), pattern + discount));
            logMsgs.add(MessageFormat.format(InfoConstant.getLogInfo("TEA_DISCOUNT"), pattern + discount));
        }
        return discount;
    }
}