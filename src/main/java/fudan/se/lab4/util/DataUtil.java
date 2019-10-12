package fudan.se.lab4.util;

import fudan.se.lab4.constant.FileConstant;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class DataUtil {
    /**
     * This function should search database by given table and key
     *
     * @param table the name of table
     * @param key   the key of entry
     * @return an array with the information of the given key
     */
    public static String[] readByKeyInTable(String table, String key) {
        return FileUtil.readByField(key, MessageFormat.format(FileConstant.DATA_CSV, table), 0);
    }

    public static String[] readByNameInTable(String table, String key) {
        return FileUtil.readByField(key, MessageFormat.format(FileConstant.DATA_CSV, table), 1);
    }

    public static void writeIntoTable(String table, String[] array) {
        FileUtil.write(array, MessageFormat.format(FileConstant.DATA_CSV, table));
    }

    public static List<String> readCountryList() {
        return FileUtil.readByColumn(0, "country",
                MessageFormat.format(FileConstant.DATA_CSV, "language"));
    }

    public static List<String> readCurrencyList(String country) {
        String[] entry = FileUtil.readByField(
                country, MessageFormat.format(FileConstant.DATA_CSV, "currency"), 0
        );
        return Arrays.asList(entry).subList(2, entry.length - 1);
    }

    public static List<String> readDrinkList(String country) {
        return FileUtil.readByColumn(1, "name",
                MessageFormat.format(FileConstant.DATA_CSV, "drinks/" + country));
    }

    public static List<String> readIngredientList(String country) {
        return FileUtil.readByColumn(1, "name",
                MessageFormat.format(FileConstant.DATA_CSV, "ingredients/" + country));
    }

    public static List<String> readStrategyClassList(String language) {
        return FileUtil.readByColumn(0, "name",
                MessageFormat.format(FileConstant.DATA_CSV, "strategies/" + language));
    }

    public static List<String> readStrategyList(String language) {
        return FileUtil.readByColumn(1, "name",
                MessageFormat.format(FileConstant.DATA_CSV, "strategies/" + language));
    }

    public static double getPriceInCurrencyPrices(String currencyPricesString, String priceArea) {
        String[] currencyPrices = currencyPricesString.split(";");
        double price = 0;
        for (String currencyPrice : currencyPrices) {
            int pos = currencyPrice.indexOf(":");
            if (priceArea.equals(currencyPrice.substring(0, pos))) {
                price = Double.valueOf(currencyPrice.substring(pos + 1));
                break;
            }
        }
        return price;
    }
}
