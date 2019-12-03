package cs.hku.hk.memome.dao;

import cs.hku.hk.memome.model.User;

public interface UserDao {

    User getUserByEmail(String email);

    int getCoinsByEmail(String email);

    void insertUser(User user);

    void deleteUserByEmail(String email);

    void updateCoinByEmailAndQuantity(String email, int quantity);

}
