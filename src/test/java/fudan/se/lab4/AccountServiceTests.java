package fudan.se.lab4;

import fudan.se.lab4.constant.FileConstant;
import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.User;
import fudan.se.lab4.service.AccountService;
import fudan.se.lab4.service.impl.AccountServiceImpl;
import fudan.se.lab4.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.MessageFormat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountServiceTests {
    private AccountService accountService;

    /**
     * set up account service
     */
    @Before
    public void setUp() {
        accountService = new AccountServiceImpl();
    }

    /**
     * set account service to null after testing
     */
    @After
    public void tearDown() {
        accountService = null;
    }

//    @Rule
//    public ExpectedException thrown = ExpectedException.none();

    /*
      generate a valid user and login with it
      should successfully login
     */
    @Test
    public void testValidUserLogin() {
        User user = new User();
        user.setName("starbb_kylin");
        user.setPassword("123_abc_");

        assertTrue("Valid user login failed. ", accountService.login(user));
    }

    /**
     * generate an invalid null user
     * should throw runtime exception with null user message
     */
    @Test
    public void testNullUserLogin() {
        User user = null;

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(MessageFormat.format(
//                InfoConstant.getInfo("FAIL_TO_LOG_IN"), InfoConstant.getInfo("NULL_USER")
//        ));
        assertFalse("Null user login test failed. ", accountService.login(user));
    }

    /**
     * generate an invalid user with empty username and password
     * should throw runtime exception with empty info message
     */
    @Test
    public void testEmptyUsernameLogin() {
        User user = new User();

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(MessageFormat.format(
//                InfoConstant.getInfo("FAIL_TO_LOG_IN"), InfoConstant.getInfo("EMPTY_FIELD")
//        ));
        assertFalse("Empty user login test failed. ", accountService.login(user));
    }

    /**
     * generate an invalid user with not existing username
     * should throw runtime exception with not exist message
     */
    @Test
    public void testNotExistingUsernameLogin() {
        User user = new User();
        user.setName("abcd");
        user.setPassword("123_abc_");

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(InfoConstant.getInfo("FAIL_TO_LOG_IN_NOT_EXIST"));
        assertFalse("Not existing user login test failed. ", accountService.login(user));
    }

    /**
     * generate a valid user with wrong password and login with it
     * should fail to login, and throw runtime exception
     */
    @Test
    public void testWrongPasswordValidUserLogin() {
        User user = new User();
        user.setName("starbb_kylin");
        user.setPassword("123_");

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(InfoConstant.getInfo("FAIL_TO_LOG_IN_WRONG_INFO"));
        assertFalse("Wrong password valid user login test failed. ", accountService.login(user));
    }

    /**
     * generate a valid user and sign up with it
     * should successfully sign up and write into the file
     * then delete it from the file
     */
    @Test
    public void testValidUserSignup() {
        String username = "starbb_programmerwcn";
        User user = new User();
        user.setName(username);
        user.setPassword("123456_wcn");

        assertTrue("Valid user sign up failed. ", accountService.signup(user));
        // if sign up succeeded, delete the user from the file
        assertTrue("Fail to write into the file",
                FileUtil.delete(username, MessageFormat.format(FileConstant.DATA_CSV, "user")));
    }

    /**
     * generate an invalid null user
     * should throw runtime exception with null user message
     */
    @Test
    public void testNullUserSignup() {
        User user = null;

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(MessageFormat.format(
//                InfoConstant.getInfo("FAIL_TO_SIGN_UP"), InfoConstant.getInfo("NULL_USER")
//        ));
        assertFalse("Null user sign up test failed. ", accountService.signup(user));
    }

    /**
     * generate an invalid user with empty username and password
     * should throw runtime exception with empty info message
     */
    @Test
    public void testEmptyUsernameSignup() {
        User user = new User();

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(MessageFormat.format(
//                InfoConstant.getInfo("FAIL_TO_SIGN_UP"), InfoConstant.getInfo("EMPTY_FIELD")
//        ));
        assertFalse("Empty user sign up test failed. ", accountService.signup(user));
    }

    /**
     * generate an invalid user with userName already existing
     * should throw runtime exception
     */
    @Test
    public void testExistedUsernameSignup() {
        User user = new User();
        user.setName("starbb_tmx");
        user.setPassword("123_abc");

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(MessageFormat.format(InfoConstant.getInfo("USER_EXIST"), user.getName()));
        assertFalse("Existed username sign up test failed. ", accountService.signup(user));
    }

    /**
     * generate an invalid user with userName or password against the naming rule
     * should throw runtime exception
     */
    @Test
    public void testInvalidUsernameSignup() {
        User user = new User();
        user.setName("abcde");
        user.setPassword("1234567890");

//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(
//                MessageFormat.format(InfoConstant.getInfo("FAIL_TO_SIGN_UP"),
//                        MessageFormat.format(
//                                InfoConstant.getInfo("INVALID_INFO"),
//                                InfoConstant.getInfo("USERNAME_OR_PASSKEY")
//                        )
//                )
//        );
        assertFalse("Invalid username sign up test failed. ", accountService.signup(user));
    }

    /**
     * generate a valid user and login
     * call check status
     * should return true
     */
    @Test
    public void testCheckStatusIsOnline() {
        User validUser = new User();
        validUser.setName("starbb_tmx");
        validUser.setPassword("tmx_99_lyf");
        accountService.login(validUser);

        assertTrue("Check is online failed. ", accountService.checkStatus());
    }

    /**
     * check status without login
     * should return false
     */
    @Test
    public void testCheckStatusIsNotOnline() {
        assertFalse("Check is not online failed. ", accountService.checkStatus());
    }

    /**
     * input valid name, assert checkName() return true
     * notice that either of number, English characters, _ is allowed
     */
    @Test
    public void testCheckNameValid() {
        String validName1 = "starbb_wcn123";
        String validName2 = "starbb_programmerwcn";
        String validName3 = "starbb_1234567890";
        String validName4 = "starbb_____________";

        assertTrue(accountService.checkName(validName1));
        assertTrue(accountService.checkName(validName2));
        assertTrue(accountService.checkName(validName3));
        assertTrue(accountService.checkName(validName4));
    }

    /**
     * input a null string, assert checkName() return false
     */
    @Test
    public void testCheckNameNull() {
        String nullString = null;

        assertFalse(accountService.checkName(nullString));
    }

    /**
     * input a name, length<8, assert checkName() return false
     */
    @Test
    public void testCheckNameTooShort() {
        String tooShort = "starbb_";

        assertFalse(accountService.checkName(tooShort));
    }

    /**
     * input a name, length>50, assert checkName() return false
     */
    @Test
    public void testCheckNameTooLong() {
        String tooLong = "starbb_starbb_starbb_starbb_starbb_starbb_starbb_starbb_starbb_starbb_";

        assertFalse(accountService.checkName(tooLong));
    }

    /**
     * input a name, not begin with"starbb_", assert checkName() return false
     */
    @Test
    public void testCheckNameWrongBegin() {
        String wrongBegin = "st_wcn1234567";

        assertFalse(accountService.checkName(wrongBegin));
    }

    /**
     * input a name with invalid elements, assert checkName() return false
     */
    @Test
    public void testCheckNameInvalidWords() {
        String invalidWords = "starbb_wcn@123!";

        assertFalse(accountService.checkName(invalidWords));
    }

    /**
     * input some dirty names with strange characters, assert checkName() return false
     */
    @Test
    public void testCheckNameDirty() {
        String dirty1 = "!=&&";
        String dirty2 = "(#`O′)🌫鵡🐷";

        assertFalse(accountService.checkName(dirty1));
        assertFalse(accountService.checkName(dirty2));
    }

    /* check password */

    /**
     * input valid password as required, assert checkPassword() return true
     */
    @Test
    public void testCheckPasswordValid() {
        String valid1 = "christine_123";
        String valid2 = "Chris_12345_Pass";

        assertTrue(accountService.checkPassword(valid1));
        assertTrue(accountService.checkPassword(valid2));
    }

    /**
     * input a null string, assert checkPassword() return false
     */
    @Test
    public void testCheckPasswordNull() {
        String nullString = null;

        assertFalse(accountService.checkPassword(nullString));
    }

    /**
     * input password, length<8, assert checkPassword() return false
     */
    @Test
    public void testCheckPasswordTooShort() {
        String tooShort = "ch_12";

        assertFalse(accountService.checkPassword(tooShort));
    }

    /**
     * input password, length>100, assert checkPassword() return false
     */
    @Test
    public void testCheckPPasswordTooLong() {
        String tooLong = "programmer123_programmer123_programmer123_programmer123_" +
                "programmer123_programmer123_programmer123_programmer123_programmer123_";

        assertFalse(accountService.checkPassword(tooLong));
    }

    /**
     * input password which lacks either English characters, numbers, or _
     * assert checkPassword() return false
     */
    @Test
    public void testCheckPasswordLackElement() {
        String lack1 = "programmerwcn123";
        String lack2 = "1234567890_";
        String lack3 = "programmer_wcn";
        // when composition error and length error happens at the same time, log the former one
        String lack4 = "_____";
        String lack5 = "12345";
        String lack6 = "wcn";

        assertFalse(accountService.checkPassword(lack1));
        assertFalse(accountService.checkPassword(lack2));
        assertFalse(accountService.checkPassword(lack3));
        assertFalse(accountService.checkPassword(lack4));
        assertFalse(accountService.checkPassword(lack5));
        assertFalse(accountService.checkPassword(lack6));
    }

    /**
     * input passwords which includes invalid characters
     * assert checkPassword() return false
     */
    @Test
    public void testCheckPasswordInvalidWord() {
        String invalid = "christine!123#";

        assertFalse(accountService.checkPassword(invalid));
    }

    /**
     * input some dirty passwords which includes strange characters and websites
     * assert checkPassword() return false
     */
    @Test
    public void testCheckPasswordDirty() {
        String dirty1 = "&&!=";
        String dirty2 = "😏😏薹(*^_^*)";
        String dirty3 = "https://blog.csdn.net/sss2113923/article/details/78405575";

        assertFalse(accountService.checkPassword(dirty1));
        assertFalse(accountService.checkPassword(dirty2));
        assertFalse(accountService.checkPassword(dirty3));
    }

}

