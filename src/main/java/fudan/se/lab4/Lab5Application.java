package fudan.se.lab4;

import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.dto.Ingredient;
import fudan.se.lab4.dto.Order;
import fudan.se.lab4.dto.OrderItem;
import fudan.se.lab4.dto.PaymentInfo;
import fudan.se.lab4.entity.User;
import fudan.se.lab4.service.AccountService;
import fudan.se.lab4.service.ClientService;
import fudan.se.lab4.service.OrderService;
import fudan.se.lab4.service.impl.AccountServiceImpl;
import fudan.se.lab4.service.impl.ClientServiceImpl;
import fudan.se.lab4.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.MessageFormat;
import java.util.*;

@SpringBootApplication
public class Lab5Application {
    private static Logger logger = LoggerFactory.getLogger(Lab5Application.class);
    private static Scanner scanner = new Scanner(System.in);

    private static AccountService accountService = new AccountServiceImpl();
    private static ClientService clientService = new ClientServiceImpl();
    private static OrderService orderService = new OrderServiceImpl();

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);

        // choose country/region and language first
        clientService.setCountry(getCountry(clientService.getCountryList()));
        clientService.setLocaleByCountry();

        // choose currency according to country
        clientService.setCurrency(getCurrency(clientService.getCurrencyList()));

        // strategy choose
        clientService.addStrategy(getStrategies(clientService.getStrategyList(), clientService.getStrategyClassList()));

        // sign up or sign in
        int status = getSignUpOrSignIn();
        while (!accountService.checkStatus()) {
            User user = getInputUser(status);
            if (user == null)// invalid user
                continue;
            switch (status) {
                case 1: // sign up
                    if (accountService.signup(user)) // sign up successfully
                        accountService.login(user); // then login automatically
                    break;
                case 2: // login
                    accountService.login(user);
                    break;
            }
        }

        // check status
        while (accountService.checkStatus()) {
            // choose drink(s) and create order
            Order order = orderService.createOrder(
                    getOrderItems(clientService.getDrinkList(), clientService.getIngredientList())
            );
            logger.info(MessageFormat.format(InfoConstant.getInfo("ORDER_INFO"), order.getId()));

            // pay the order
            printPay(orderService.pay(order, clientService.getCountry(), clientService.getCurrency(), clientService.getStrategies()));
        }
    }

    /**
     * this function request whether user want to sign in or sign up
     *
     * @return 1 for sign up, 2 for sign in
     */
    private static int getSignUpOrSignIn() {
        logger.info(InfoConstant.getInfo("REQUEST_SIGN_IN_OR_SIGN_UP"));
        logger.info("1. " + InfoConstant.getInfo("SIGN_UP"));
        logger.info("2. " + InfoConstant.getInfo("SIGN_IN"));
        return getInputNum(2);
    }

    /**
     * Request the user to input the meta information of a new account and return a user object
     * Input checking is also realized here
     *
     * @param status 1 means ready to sign up, 2 means ready to login
     * @return generated user instance
     */
    private static User getInputUser(int status) {
        while (true) {
            // hints
            logger.info(status == 1 ? InfoConstant.getInfo("BEGIN_SIGNUP") : InfoConstant.getInfo("BEGIN_LOGIN"));
            logger.info(InfoConstant.getInfo("REQUEST_USER_INFO"));

            // input
            String userInput = scanner.nextLine();
            // check
            String[] userInfos = userInput.split(" ");
            if (userInfos.length != 2 || "".equals(userInfos[0]) || "".equals(userInfos[1])) {
                logger.info(MessageFormat.format(
                        InfoConstant.getInfo("INVALID_INFO"),
                        InfoConstant.getInfo("USERNAME_OR_PASSKEY"),
                        InfoConstant.getInfo("EMPTY_FIELD")
                ));
                logger.error(MessageFormat.format(
                        InfoConstant.getLogInfo("INVALID_INFO"),
                        InfoConstant.getLogInfo("USERNAME_OR_PASSKEY"),
                        InfoConstant.getLogInfo("EMPTY_FIELD")
                ));
                continue;
            }
            // return
            User user = new User();
            user.setName(userInfos[0]);
            user.setPassword(userInfos[1]);
            return user;
        }
    }

    private static String getCountry(List<String> countries) {
        // hints
        logger.info(InfoConstant.getInfo("REQUEST_COUNTRY_INFO"));
        for (String country : countries) {
            logger.info((countries.indexOf(country) + 1) + ". " + country);
        }
        // input and check
        int num = getInputNum(countries.size());
        // return
        return countries.get(num - 1);
    }

    private static String getCurrency(List<String> currencies) {
        // hints
        logger.info(InfoConstant.getInfo("REQUEST_CURRENCY_INFO"));
        for (String country : currencies) {
            logger.info((currencies.indexOf(country) + 1) + ". " + country);
        }
        // input and check
        int num = getInputNum(currencies.size());
        // return
        return currencies.get(num - 1);
    }

    /**
     * this method gets strategies the user chooses
     *
     * @param strategies the strategies which can be selected (not including default ones)
     */
    private static List<String> getStrategies(List<String> strategies, List<String> strategyClasses) {
        List<String> chosenStrategyClasses = new ArrayList<>();

        while (true) {
            // hints
            logger.info(InfoConstant.getInfo("REQUEST_STRATEGY_INFO"));
            for (String strategy : strategies) {
                logger.info((strategies.indexOf(strategy) + 1) + ". " + strategy);
            }

            // input
            String input = scanner.nextLine();
            String[] nums = input.split(" ");

            try {
                if (nums.length > strategies.size()) {  // to many arguments
                    logger.info(MessageFormat.format(
                            InfoConstant.getInfo("INVALID_NUM"), input
                    ));
                    logger.error(MessageFormat.format(
                            InfoConstant.getLogInfo("INVALID_NUM"), input
                    ));
                } else {
                    if (!"".equals(input)) { // have some strategies to add
                        // parse int
                        int[] ints = new int[nums.length];
                        boolean valid = true;
                        for (int i = 0; i < nums.length; i++) {
                            ints[i] = Integer.parseInt(nums[i]);
                            if (ints[i] < 1 || ints[i] > strategies.size()) { // too big or too small
                                logger.info(MessageFormat.format(
                                        InfoConstant.getInfo("INVALID_NUM"),
                                        InfoConstant.getInfo("TOO_BIG_OR_TOO_SMALL_NUM")
                                ));
                                logger.error(MessageFormat.format(
                                        InfoConstant.getLogInfo("INVALID_NUM"),
                                        InfoConstant.getLogInfo("TOO_BIG_OR_TOO_SMALL_NUM")
                                ));
                                valid = false;
                                break;
                            }
                        }
                        if (!valid) // input again
                            continue;
                        // add chosen strategies
                        for (int num : ints) {
                            chosenStrategyClasses.add(strategyClasses.get(num - 1));
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                logger.info(MessageFormat.format(
                        InfoConstant.getInfo("INVALID_NUM"), InfoConstant.getInfo("ILLEGAL_INPUT")
                ));
                logger.error(MessageFormat.format(
                        InfoConstant.getLogInfo("INVALID_NUM"), InfoConstant.getLogInfo("ILLEGAL_INPUT")
                ));
            }

        }
        return chosenStrategyClasses;

    }

    private static List<OrderItem> getOrderItems(List<String> drinkList, List<String> ingredientList) {
        List<OrderItem> orderItems = new ArrayList<>();
        while (true) {
            // hints
            logger.info(InfoConstant.getInfo("REQUEST_DRINK_INFO"));
            for (String drink : drinkList) {
                logger.info((drinkList.indexOf(drink) + 1) + ". " + drink);
            }

            // get input
            int num = getInputNum(drinkList.size());
            if (num == -1) // break when get -1
                break;

            int size = getSize();

            List<Ingredient> ingredients = getIngredients(ingredientList);

            // generate the corresponding orderItem and add to the list
            orderItems.add(new OrderItem(drinkList.get(num - 1), ingredients, size));
        }
        return orderItems;
    }

    private static List<Ingredient> getIngredients(List<String> ingredientList) {
        List<Ingredient> ingredients = new ArrayList<>();
        while (true) {
            // hints
            logger.info(InfoConstant.getInfo("REQUEST_INGREDIENT_INFO"));
            for (String ingredient : ingredientList) {
                logger.info((ingredientList.indexOf(ingredient) + 1) + ". " + ingredient);
            }

            // get input
            int num = getInputNum(ingredientList.size());
            if (num == -1) // break when get -1
                break;

            // generate the corresponding ingredient and add to the list
            ingredients.add(new Ingredient(ingredientList.get(num - 1)));
        }

        return ingredients;
    }

    private static int getSize() {
        // hints
        logger.info(InfoConstant.getInfo("REQUEST_SIZE_INFO"));
        logger.info("1. " + InfoConstant.getInfo("SIZE_SMALL"));
        logger.info("2. " + InfoConstant.getInfo("SIZE_MEDIUM"));
        logger.info("3. " + InfoConstant.getInfo("SIZE_LARGE"));
        int size = getInputNum(3);
        if (size == -1) {
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_NUM"), InfoConstant.getInfo("ILLEGAL_INPUT")
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_NUM"), InfoConstant.getLogInfo("ILLEGAL_INPUT")
            ));
            return getSize();
        }
        return size;
    }

    // could receive -1
    private static int getInputNum(int max) {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number > max || number == 0 || number < -1) { // number too big or too small
                    logger.info(MessageFormat.format(
                            InfoConstant.getInfo("INVALID_NUM"), InfoConstant.getInfo("TOO_BIG_OR_TOO_SMALL_NUM")
                    ));
                    logger.error(MessageFormat.format(
                            InfoConstant.getLogInfo("INVALID_NUM"), InfoConstant.getLogInfo("TOO_BIG_OR_TOO_SMALL_NUM")
                    ));
                } else {// valid number
                    return number;
                }
            } catch (Exception e) {
                logger.info(MessageFormat.format(
                        InfoConstant.getInfo("INVALID_NUM"), InfoConstant.getInfo("ILLEGAL_INPUT")
                ));
                logger.error(MessageFormat.format(
                        InfoConstant.getLogInfo("INVALID_NUM"), InfoConstant.getLogInfo("ILLEGAL_INPUT")
                ));
            }
        }
    }

    private static void printPay(PaymentInfo paymentInfo) {
        StringBuilder s = new StringBuilder();
        s.append("\n").append(paymentInfo.getGoodsInfoString());
        s.append("\n").append(MessageFormat.format(
                InfoConstant.getInfo("PAYMENT_INFO_ORIGINAL"),
                clientService.getCurrencyLabel() + paymentInfo.getPrice()
        ));
        s.append("\n").append(MessageFormat.format(
                InfoConstant.getInfo("PAYMENT_INFO_DISCOUNT"),
                clientService.getCurrencyLabel() + paymentInfo.getDiscount()
        ));
        s.append("\n").append(MessageFormat.format(
                InfoConstant.getInfo("PAYMENT_INFO_DISCOUNT_PRICE"),
                clientService.getCurrencyLabel() + paymentInfo.getDiscountPrice()
        ));
        s.append("\n").append(InfoConstant.getInfo("PAYMENT_DISCOUNT_MSGS"));

        if (paymentInfo.getMsgs().size() == 0)
            s.append(" ").append(InfoConstant.getInfo("NULL"));
        else {
            for (String message : paymentInfo.getMsgs())
                s.append(message);
        }

        logger.info(s.toString());
    }
}
