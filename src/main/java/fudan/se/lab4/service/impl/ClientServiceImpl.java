package fudan.se.lab4.service.impl;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.service.ClientService;
import fudan.se.lab4.strategy.Strategy;
import fudan.se.lab4.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientServiceImpl implements ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private String country;
    private String[] lang = new String[2], currency = new String[2];
    private List<String> countryList, currencyList, drinkList, ingredientList, strategyList, strategyClassList;
    private List<Strategy> strategies = new ArrayList<>();

    /**
     * use this method to get set country
     *
     * @return country the client set
     */
    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * this method sets country list a client can choose
     */
    private void initCountryList() {
        countryList = DataUtil.readCountryList();
    }

    /**
     * use this method to get set country list
     *
     * @return array list of country names
     */
    @Override
    public List<String> getCountryList() {
        if (countryList == null)
            initCountryList();
        return countryList;
    }


    /**
     * this method sets which client locale to use
     */
    @Override
    public void setLocaleByCountry() {
        String[] langCol = DataUtil.readByKeyInTable("language", country);  // a country maps to a language
        lang[0] = langCol[1];
        lang[1] = langCol[2];
        InfoConstant.setResourceBundle(new Locale(lang[0], lang[1]));
    }

    /**
     * this method sets currencyLabel list a client can choose
     */
    private void initCurrencyList() {
        currencyList = DataUtil.readCurrencyList(country);
    }

    /**
     * use this method to get set currencyLabel (in country) list
     *
     * @return array list of currencyLabel name
     */
    @Override
    public List<String> getCurrencyList() {
        if (currencyList == null)
            initCurrencyList();
        return currencyList;
    }

    /**
     * use this method to set currencyLabel by country
     *
     * @param country which country's currencyLabel to use
     */
    public void setCurrency(String country) {
        String[] info = DataUtil.readByKeyInTable("currency", country);
        // set native currency
        currency[0] = info[0];
        currency[1] = info[1];
    }

    /**
     * use this method to get currency info
     *
     * @return currency info
     */
    public String[] getCurrency() {
        return currency;
    }

    /**
     * use this method to get set currency country
     *
     * @return currency country the client set
     */
    @Override
    public String getCurrencyCountry() {
        return currency[0];
    }

    /**
     * use this method to get set currencyLabel
     *
     * @return currencyLabel the client set
     */
    @Override
    public String getCurrencyLabel() {
        return currency[1];
    }

    /**
     * this method sets what drinks can be ordered in the given country
     */
    private void initDrinkListByCountry() {
        drinkList = DataUtil.readDrinkList(country);
    }

    /**
     * use this method to get set drink list
     *
     * @return array of drink names
     */
    @Override
    public List<String> getDrinkList() {
        if (drinkList == null)
            initDrinkListByCountry();
        return drinkList;
    }

    /**
     * this method sets what ingredients can be ordered
     */
    private void initIngredientList() {
        ingredientList = DataUtil.readIngredientList(country);
    }

    /**
     * use this method to get set ingredient list
     *
     * @return array of ingredient names
     */
    @Override
    public List<String> getIngredientList() {
        if (ingredientList == null)
            initIngredientList();
        return ingredientList;
    }

    /**
     * this method sets the class names of strategies that can be choose to use
     * default strategies' class names not included
     */
    private void initStrategyClassList() {
        strategyClassList = DataUtil.readStrategyClassList(lang[0] + "_" + lang[1]);
    }

    /**
     * use this method to get the class names of strategies that can be choose to use
     *
     * @return array list of class names
     */
    @Override
    public List<String> getStrategyClassList() {
        if (strategyClassList == null)
            initStrategyClassList();
        return strategyClassList;
    }

    /**
     * this method sets what strategies can be choose to use
     * default strategies not included
     */
    private void initStrategyList() {
        strategyList = DataUtil.readStrategyList(lang[0] + "_" + lang[1]);
    }

    /**
     * use this method to get set strategyList
     *
     * @return array list of strategy names
     */
    @Override
    public List<String> getStrategyList() {
        if (strategyList == null)
            initStrategyList();
        return strategyList;
    }

    /**
     * this method instantiate a instance of Strategy with name
     *
     * @param name class name of strategy
     * @return instance of name
     */
    private Strategy getStrategy(String name) {
        try {
            Class strategy = Class.forName("fudan.se.lab4.strategy.impl." + name);
            return (Strategy) strategy.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // will not reach here
            logger.info(e.getMessage());
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * this method adds a strategy to list can be used
     *
     * @param strategies the strategies to be added
     */
    @Override
    public void addStrategy(List<String> strategies) {
        for (String name : strategies) {
            this.strategies.add(getStrategy(name));
        }
    }

    /**
     * this method deletes a strategy from list can be used,
     * but original two strategies can't be removed
     *
     * @param strategy the strategy to be deleted
     */
    @Override
    public void deleteStrategy(Strategy strategy) {
        strategies.remove(strategy);
    }

    /**
     * use this method to get available strategies
     *
     * @return strategies available
     */
    @Override
    public List<Strategy> getStrategies() {
        return strategies;
    }
}
