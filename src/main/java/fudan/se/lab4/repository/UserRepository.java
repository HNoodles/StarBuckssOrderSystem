package fudan.se.lab4.repository;

import fudan.se.lab4.entity.User;

public interface UserRepository {
    /**
     * persist user in data/user.csv
     *
     * @param user user to be created
     */
    void createUser(User user);

    /**
     * get User by name in data/user.csv
     *
     * @param name user name
     * @return user
     */
    User getUser(String name);
}
