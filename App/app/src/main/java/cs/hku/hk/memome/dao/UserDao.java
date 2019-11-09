package cs.hku.hk.memome.dao;

import cs.hku.hk.memome.model.User;

public interface UserDao {
    User getUserFromEmail(String email);
}
