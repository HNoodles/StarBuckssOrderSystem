package fudan.se.lab4.strategy.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.strategy.Strategy;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TwoBigEspressoStrategy extends Strategy {
    public TwoBigEspressoStrategy() {
        type = "CombineDiscount";
    }

    public double cost(List<Drink> drinks, Double originalPrice, String pattern) {
        List<Drink> bigEspressos = new ArrayList<>();
        double discount = 0;

        for (Drink drink : drinks) {
            if (drink.getKey().equals("espresso") && drink.getSize() == 3) {
                bigEspressos.add(drink);
            }
        }

        if (bigEspressos.size() >= 2) {
            int disNum = bigEspressos.size() / 2;
            discount = roundHalfUp(disNum * 0.2 * bigEspressos.get(0).getPrice() * 2);
            msgs.add(MessageFormat.format(InfoConstant.getInfo("ESPRESSO_DISCOUNT"), pattern + discount));
            logMsgs.add(MessageFormat.format(InfoConstant.getLogInfo("ESPRESSO_DISCOUNT"), pattern + discount));
        }

        return discount;
    }
}
