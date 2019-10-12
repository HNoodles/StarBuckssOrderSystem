package fudan.se.lab4.repository.impl;

import fudan.se.lab4.constant.FileConstant;
import fudan.se.lab4.constant.InfoConstant;
import fudan.se.lab4.entity.User;
import fudan.se.lab4.repository.UserRepository;
import fudan.se.lab4.util.FileUtil;

import java.text.MessageFormat;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void createUser(User user) {
        FileUtil.write(objectToStringArray(user), MessageFormat.format(FileConstant.DATA_CSV, "user"));
    }

    @Override
    public User getUser(String name) {
        return stringArrayToObject(FileUtil.readByField(name, MessageFormat.format(FileConstant.DATA_CSV, "user"), 0));
    }

    private String[] objectToStringArray(User user) {
        // if user already exists, throw exception
        if (FileUtil.exist(user.getName(), MessageFormat.format(FileConstant.DATA_CSV, "user"))) {
            throw new RuntimeException(MessageFormat.format(InfoConstant.getInfo("USER_EXIST"), user.getName()));
        }
        String[] array = new String[2];
        array[0] = user.getName();
        array[1] = user.getPassword();
        return array;
    }

    private User stringArrayToObject(String[] array) {
        User user = new User();
        user.setName(array[0]);
        user.setPassword(array[1]);
        return user;
    }
}
