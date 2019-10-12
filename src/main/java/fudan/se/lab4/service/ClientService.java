package fudan.se.lab4.service;

import fudan.se.lab4.strategy.Strategy;

import java.util.List;


public interface ClientService {
    /**
     * use this method to get set country list
     * @return array list of country names
     */
    List<String> getCountryList();

    /**
     * use this method to get set country
     * @return country the client set
     */
    String getCountry();

    void setCountry(String country);

    /**
     * this method sets which client locale to use
     */
    void setLocaleByCountry();

    /**
     * use this method to get set currency list
     * @return array list of currency name
     */
    List<String> getCurrencyList();

    /**
     * use this method to get set currency country
     * @return currency country the client set
     */
    String getCurrencyCountry();

    /**
     * use this method to get set currency label
     * @return currency the client set
     */
    String getCurrencyLabel();

    void setCurrency(String country);

    String[] getCurrency();

    /**
     * use this method to get set drink list
     * @return array list of drink names
     */
    List<String> getDrinkList();

    /**
     * use this method to get set ingredient list
     * @return array list of ingredient names
     */
    List<String> getIngredientList();

    /**
     * use this method to get the class names of strategies that can be choose to use
     * @return array list of class names
     */
    List<String> getStrategyClassList();

    /**
     * use this method to get set strategy list
     * @return array list of strategy names
     */
    List<String> getStrategyList();

    /**
     * this method adds a strategy to list can be used
     * @param strategies the strategies to be added
     */
    void addStrategy(List<String> strategies);

    /**
     * this method deletes a strategy from list can be used
     * @param strategy the strategy to be deleted
     */
    void deleteStrategy(Strategy strategy);

    /**
     * use this method to get set available strategies
     * @return strategies available
     */
    List<Strategy> getStrategies();
}
