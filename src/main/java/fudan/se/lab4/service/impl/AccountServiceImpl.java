package fudan.se.lab4.service.impl;

import fudan.se.lab4.constant.FileConstant;
import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.User;
import fudan.se.lab4.repository.UserRepository;
import fudan.se.lab4.repository.impl.UserRepositoryImpl;
import fudan.se.lab4.service.AccountService;
import fudan.se.lab4.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class AccountServiceImpl implements AccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private UserRepository userRepository = new UserRepositoryImpl();
    private boolean isOnline = false;

    /**
     * Check if the user is null or has empty username or password
     * Check if the user exists
     * Get the existing user from file
     * if login successfully, isOnline would be set to true
     *
     * @param user: the generated object from application
     * @return true if login successfully, otherwise false
     */
    @Override
    public boolean login(User user) {
        // if user is null or userName/password is "",throw run time exception
        if (checkUserInvalid(user,
                InfoConstant.getInfo("FAIL_TO_LOG_IN"),
                InfoConstant.getLogInfo("FAIL_TO_LOG_IN")))
            return false;
        // check if user exists
        if (!FileUtil.exist(user.getName(), MessageFormat.format(FileConstant.DATA_CSV, "user"))) {
            logger.info(InfoConstant.getInfo("FAIL_TO_LOG_IN_NOT_EXIST"));
            logger.error(InfoConstant.getLogInfo("FAIL_TO_LOG_IN_NOT_EXIST"));
            return false;
        }
        // check if userName & password equal
        User existed = userRepository.getUser(user.getName());
        if (existed.getPassword().equals(user.getPassword())) {
            // login successfully, update status, print logger info
            logger.info(MessageFormat.format(InfoConstant.getInfo("SUCCESS_TO_LOG_IN"), user.getName()));
            logger.warn(MessageFormat.format(InfoConstant.getLogInfo("SUCCESS_TO_LOG_IN"), user.getName()));
            isOnline = true;
            return true;
        } else {
            // login failed, update status, print logger info
            isOnline = false;
            logger.info(InfoConstant.getInfo("FAIL_TO_LOG_IN_WRONG_INFO"));
            logger.error(InfoConstant.getLogInfo("FAIL_TO_LOG_IN_WRONG_INFO"));
            return false;
        }
    }

    /**
     * Check if the user is null or has empty username or password
     * Check if the user has repeated username
     * Check if the user has valid name and password
     *
     * @param user: the generated object from application, should have unique username
     * @return true if sign up successfully, otherwise false
     */
    @Override
    public boolean signup(User user) {
        // if user is null, or empty user name or password
        if (checkUserInvalid(user,
                InfoConstant.getInfo("FAIL_TO_SIGN_UP"),
                InfoConstant.getLogInfo("FAIL_TO_SIGN_UP")))
            return false;
        // if user already exists, throw exception
        if (FileUtil.exist(user.getName(), MessageFormat.format(FileConstant.DATA_CSV, "user"))) {
            logger.info(MessageFormat.format(InfoConstant.getInfo("USER_EXIST"), user.getName()));
            logger.error(MessageFormat.format(InfoConstant.getLogInfo("USER_EXIST"), user.getName()));
            return false;
        }
        // if username or password is invalid
        if (!checkName(user.getName()) || !checkPassword(user.getPassword())) {
            logger.info(
                    MessageFormat.format(InfoConstant.getInfo("FAIL_TO_SIGN_UP"),
                            MessageFormat.format(InfoConstant.getInfo("INVALID_INFO"),
                                    InfoConstant.getInfo("USERNAME_OR_PASSKEY"))
                    )
            );
            logger.error(
                    MessageFormat.format(InfoConstant.getLogInfo("FAIL_TO_SIGN_UP"),
                            MessageFormat.format(InfoConstant.getLogInfo("INVALID_INFO"),
                                    InfoConstant.getLogInfo("USERNAME_OR_PASSKEY"))
                    )
            );
            return false;
        }
        // create user and print logger info
        userRepository.createUser(user);
        logger.info(MessageFormat.format(InfoConstant.getInfo("SUCCESS_TO_SIGN_UP"), user.getName()));
        logger.warn(MessageFormat.format(InfoConstant.getLogInfo("SUCCESS_TO_SIGN_UP"), user.getName()));
        return true;
    }

    /**
     * Check current status of the user
     *
     * @return true if has logged in, otherwise false
     */
    @Override
    public boolean checkStatus() {
        return isOnline;
    }

    /**
     * Written to check if user is null or has empty username or password
     *
     * @param user: user to be checked
     * @return true if invalid, else false
     */
    private boolean checkUserInvalid(User user, String info, String error) {
        if (user == null) { // null pointer
            logger.info(MessageFormat.format(info, InfoConstant.getInfo("NULL_USER")));
            logger.error(MessageFormat.format(error, InfoConstant.getLogInfo("NULL_USER")));
            return true;
        } else if (user.getName() == null || user.getPassword() == null
                || user.getName().equals("") || user.getPassword().equals("")) {
            // null pointer, or empty user name or password
            logger.info(MessageFormat.format(info, InfoConstant.getInfo("EMPTY_FIELD")));
            logger.error(MessageFormat.format(error, InfoConstant.getLogInfo("EMPTY_FIELD")));
            return true;
        }
        return false;
    }

    /**
     * checks user name, takes prefix, pattern and length into account
     *
     * @param name: user name to be checked
     * @return true if valid, otherwise throws RuntimeException
     */
    @Override
    public boolean checkName(String name) {
        if (name == null) { // not null
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("USER_NAME"),
                    InfoConstant.getInfo("USERNAME_NULL")
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("USER_NAME"),
                    InfoConstant.getLogInfo("USERNAME_NULL")
            ));
            return false;
        } else if (!name.startsWith(InfoConstant.getInfo("USER_NAME_PREFIX"))) { // match prefix
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("USER_NAME"),
                    MessageFormat.format(
                            InfoConstant.getInfo("USERNAME_PREFIX"),
                            InfoConstant.getInfo("USER_NAME_PREFIX")
                    )
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("USER_NAME"),
                    MessageFormat.format(
                            InfoConstant.getLogInfo("USERNAME_PREFIX"),
                            InfoConstant.getLogInfo("USER_NAME_PREFIX")
                    )
            ));
            return false;
        } else if (!name.matches(InfoConstant.getInfo("USER_NAME_PATTERN"))) { // match pattern
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("USER_NAME"),
                    InfoConstant.getInfo("USERNAME_PATTERN")
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("USER_NAME"),
                    InfoConstant.getLogInfo("USERNAME_PATTERN")
            ));
            return false;
        } else if (name.length() < 8 || name.length() >= 50) { // match length
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("USER_NAME"),
                    InfoConstant.getInfo("USERNAME_LENGTH")
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("USER_NAME"),
                    InfoConstant.getLogInfo("USERNAME_LENGTH")
            ));
            return false;
        }
        // valid user name
        logger.info(MessageFormat.format(
                InfoConstant.getInfo("VALID_INFO"), InfoConstant.getInfo("USER_NAME")
        ));
        return true;
    }

    /**
     * checks password, takes pattern and length into account
     *
     * @param password: user password to be checked
     * @return true if valid, otherwise throws RuntimeException
     */
    @Override
    public boolean checkPassword(String password) {
        if (password == null) { // not null
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("PASSKEY"),
                    InfoConstant.getInfo("PASSKEY_NULL")
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("PASSKEY"),
                    InfoConstant.getLogInfo("PASSKEY_NULL")
            ));
            return false;
        } else if (!password.matches(InfoConstant.getInfo("PASS_KEY_PATTERN"))) { // match pattern
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("PASSKEY"),
                    MessageFormat.format(
                            InfoConstant.getInfo("PASSKEY_PATTERN"),
                            InfoConstant.getInfo("PASS_KEY_PATTERN")
                    )
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("PASSKEY"),
                    MessageFormat.format(
                            InfoConstant.getLogInfo("PASSKEY_PATTERN"),
                            InfoConstant.getLogInfo("PASS_KEY_PATTERN")
                    )
            ));
            return false;
        } else if (password.length() < 8 || password.length() >= 100) { // match length
            logger.info(MessageFormat.format(
                    InfoConstant.getInfo("INVALID_INFO"),
                    InfoConstant.getInfo("PASSKEY"),
                    InfoConstant.getInfo("PASSKEY_LENGTH")
            ));
            logger.error(MessageFormat.format(
                    InfoConstant.getLogInfo("INVALID_INFO"),
                    InfoConstant.getLogInfo("PASSKEY"),
                    InfoConstant.getLogInfo("PASSKEY_LENGTH")
            ));
            return false;
        }
        // valid password
        logger.info(MessageFormat.format(
                InfoConstant.getInfo("VALID_INFO"), InfoConstant.getInfo("PASSKEY")
        ));
        return true;
    }

}
