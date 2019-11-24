package cs.hku.hk.memome.jdbc;

import org.junit.Test;

import cs.hku.hk.memome.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserJdbcDaoTest {

    private UserJdbcDao userJdbcDao = new UserJdbcDao();
    private String testEmail = "test@gmail.com";
    private String testUsername = "test1";
    private int testCoin = 1;
    private String testPasscode = "test1";

    private String mockEmail = "mockuser000@gmail.com";
    private String mockUsername = "mockuser";
    private int mockCoin = 10;
    private String mockPasscode = "mockmock";
    private byte[] mockProfilePhoto = null;

    private int updateQuantity = 3;


    //getUserByEmail should return a user if email exists
    @Test
    public void test1() {
        User user1 = userJdbcDao.getUserByEmail(testEmail);
        assertEquals(user1.getUsername(), testUsername);
        assertEquals(user1.getCoin(), testCoin);
        assertEquals(user1.getPasscode(), testPasscode);
    }

    //getUserByEmail should return null if email does not exist
    @Test
    public void test2() {
        User user = userJdbcDao.getUserByEmail("notauser@gmail.com");
        assertNull(user);
    }

    //insertUser should insert a user into the database successfully
    @Test
    public void test3() {
        User mockUser = new User();
        mockUser.setUsername(mockUsername);
        mockUser.setEmail(mockEmail);
        mockUser.setProfilePhoto(mockProfilePhoto);
        mockUser.setPasscode(mockPasscode);
        mockUser.setCoin(mockCoin);

        User beforeInsert = userJdbcDao.getUserByEmail(mockEmail);
        assertNull(beforeInsert);

        userJdbcDao.insertUser(mockUser);
        User afterInsert = userJdbcDao.getUserByEmail(mockEmail);
        assertEquals(afterInsert.getEmail(), mockUser.getEmail());
        assertEquals(afterInsert.getPasscode(), mockUser.getPasscode());
        assertEquals(afterInsert.getCoin(), mockUser.getCoin());
        assertEquals(afterInsert.getUsername(), mockUser.getUsername());
    }

    //updateCoinByEmailAndQuantity should update the coin value
    @Test
    public void test4() {
        User beforeUpdate = userJdbcDao.getUserByEmail(mockEmail);
        int coinBeforeUpdate = beforeUpdate.getCoin();

        userJdbcDao.updateCoinByEmailAndQuantity(mockEmail, updateQuantity);

        User afterUpdate = userJdbcDao.getUserByEmail(mockEmail);
        int coinAfterUpdate = afterUpdate.getCoin();

        assertEquals(updateQuantity, coinAfterUpdate - coinBeforeUpdate);
    }

    //deleteUserByEmail should delete the user with corresponding email successfully
    @Test
    public void test5(){
        User beforeDeletion = userJdbcDao.getUserByEmail(mockEmail);
        assertNotNull(beforeDeletion);

        userJdbcDao.deleteUserByEmail(mockEmail);

        User afterDeletion = userJdbcDao.getUserByEmail(mockEmail);
        assertNull(afterDeletion);
    }
}
