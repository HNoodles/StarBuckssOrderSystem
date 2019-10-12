package fudan.se.lab4.strategy.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.strategy.Strategy;

import java.text.MessageFormat;
import java.util.List;

public class MoneyOffStrategy extends Strategy {
    public MoneyOffStrategy() {
        type = "MoneyOff";
    }

    /**
     * this method determines for a specific strategy, how much
     * an order cost and return
     * <p>
     * details:
     *
     * @param drinks        the drinks in the order to analyze
     * @param originalPrice the original total price of the drinks
     * @return discount of applying the strategy
     */
    @Override
    public double cost(List<Drink> drinks, Double originalPrice, String pattern) {
        int count = 0;
        double cost = originalPrice;

        while ((cost -= 100) > 0) {
            count++;
        }
        msgs.add(MessageFormat.format(InfoConstant.getInfo("MONEY_OFF"), pattern + roundHalfUp(count * 30)));
        logMsgs.add(MessageFormat.format(InfoConstant.getLogInfo("MONEY_OFF"), pattern + roundHalfUp(count * 30)));

        return roundHalfUp(count * 30);
    }
}
