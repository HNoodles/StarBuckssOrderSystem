package fudan.se.lab4.strategy;

import fudan.se.lab4.entity.Drink;

import java.util.ArrayList;
import java.util.List;

public abstract class Strategy {
    protected String type;
    protected List<String> msgs = new ArrayList<>();
    protected List<String> logMsgs = new ArrayList<>();

    public List<String> getMsgs() {
        return msgs;
    }

    public List<String> getLogMsgs() {
        return logMsgs;
    }

    /**
     * this method determines for a specific strategy, how much
     * an order cost and return
     *
     * @param drinks        the drinks in the order to analyze
     * @param originalPrice the original total price of the drinks
     * @return cost of applying the strategy
     */
    public abstract double cost(List<Drink> drinks, Double originalPrice, String pattern);

    /**
     * This method implements the rounding of 2-bit precision
     *
     * @param num the price not rounded
     * @return the rounded price
     */
    protected double roundHalfUp(double num) {
        return (double) Math.round(num * 100) / 100;
    }

    public String getType() {
        return type;
    }
}
