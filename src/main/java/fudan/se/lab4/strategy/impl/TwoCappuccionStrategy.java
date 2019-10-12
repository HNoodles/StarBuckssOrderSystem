package fudan.se.lab4.strategy.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.strategy.Strategy;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TwoCappuccionStrategy extends Strategy {
    public TwoCappuccionStrategy() {
        type = "CombineDiscount";
    }

    public double cost(List<Drink> drinks, Double originalPrice, String pattern) {
        double discount = 0;
        List<Drink> cappuccinos = new ArrayList<>();
        for (Drink drink : drinks) {
            if (drink.getKey().equals("cappuccino"))
                cappuccinos.add(drink);
        }

        if (cappuccinos.size() >= 2) {
            int disNum = cappuccinos.size() / 2;
            discount = roundHalfUp(disNum * 0.5 * cappuccinos.get(0).getPrice());
            msgs.add(MessageFormat.format(InfoConstant.getInfo("CAPPUCCINO_DISCOUNT"), pattern + discount));
            logMsgs.add(MessageFormat.format(InfoConstant.getLogInfo("CAPPUCCINO_DISCOUNT"), pattern + discount));
        }

        return discount;
    }
}
