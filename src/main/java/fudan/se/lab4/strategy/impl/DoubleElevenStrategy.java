package fudan.se.lab4.strategy.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.Drink;
import fudan.se.lab4.strategy.Strategy;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

public class DoubleElevenStrategy extends Strategy {
    public DoubleElevenStrategy() {
        type = "DoubleEleven";
    }

    /**
     * this method determines for a specific strategy, how much
     * an order cost and return
     * <p>
     * details: When the date is 11.11, all drinks are 50% off
     *
     * @param drinks        the drinks in the order to analyze
     * @param originalPrice the original total price of the drinks
     * @return discount of applying the strategy
     */
    @Override
    public double cost(List<Drink> drinks, Double originalPrice, String pattern) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1, // Calendar's month is from 0 to 11
                date = calendar.get(Calendar.DAY_OF_MONTH);

        if (month == 11 && date == 11) {
            msgs.add(MessageFormat.format(InfoConstant.getInfo("DOUBLE_ELEVEN_DISCOUNT"), pattern + roundHalfUp(originalPrice * 0.5)));
            logMsgs.add(MessageFormat.format(InfoConstant.getLogInfo("DOUBLE_ELEVEN_DISCOUNT"), pattern + roundHalfUp(originalPrice * 0.5)));
            return roundHalfUp(originalPrice * 0.5);
        } else return 0.0;
    }
}
