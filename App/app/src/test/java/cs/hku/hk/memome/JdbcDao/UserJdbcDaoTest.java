package cs.hku.hk.memome.JdbcDao;

import org.junit.Test;

import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.User;

public class UserJdbcDaoTest {

    private UserJdbcDao userJdbcDao;

    @Test
    public void getUserFromEmail_ShouldReturnAUserIfEmailExists() {
        userJdbcDao = new UserJdbcDao();
        User user = userJdbcDao.getUserFromEmail("lerong@hku.hk");
        System.out.println(user.getUsername());
    }
}
