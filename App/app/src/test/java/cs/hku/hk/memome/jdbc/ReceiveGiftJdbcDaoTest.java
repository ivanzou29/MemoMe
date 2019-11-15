package cs.hku.hk.memome.jdbc;

import org.junit.Test;

import java.util.Collection;

import cs.hku.hk.memome.model.ReceiveGift;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReceiveGiftJdbcDaoTest {
    ReceiveGiftJdbcDao receiveGiftJdbcDao = new ReceiveGiftJdbcDao();
    String testPostId = "b";
    String testGiftName = "flower";
    int testQuantity = 1;

    String mockPostId = "e";
    String mockGiftName = "cappuccino";
    int mockGiftQuantity = 5;
    int mockUpdateQuantity = -1;

    //getReceiveGiftsByPostId should return all gifts and quantities for a certain post
    @Test
    public void test1() {
        Collection<ReceiveGift> receiveGifts = receiveGiftJdbcDao.getReceiveGiftsByPostId(testPostId);

        assertEquals(receiveGifts.size(), 2);
    }

    //getReceiveGiftsByPostId should return empty collection if the post does not exist or has no
    @Test
    public void test2() {
        Collection<ReceiveGift> receiveGifts = receiveGiftJdbcDao.getReceiveGiftsByPostId("not a post");

        assertEquals(receiveGifts.size(), 0);
    }

    //getGiftQuantityFromPostIdAndGiftName should return the correct quantity of a gift
    @Test
    public void test3() {
        int quantity = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(testPostId, testGiftName);
        assertEquals(testQuantity, quantity);
    }

    //getGiftQuantityFromPostIdAndGiftName should return 0 if no such gift received by the post
    @Test
    public void test4() {
        int quantity = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(testPostId, "chocolate");
        assertEquals(0, quantity);
    }

    //insertReceiveGift should successfully insert the receive gift record
    @Test
    public void test5() {
        ReceiveGift receiveGift = new ReceiveGift(mockPostId, mockGiftName, mockGiftQuantity);
        int quantityBeforeInsert = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(mockPostId, mockGiftName);
        assertEquals(0, quantityBeforeInsert);

        receiveGiftJdbcDao.insertReceiveGift(receiveGift);

        int quantityAfterInsert = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(mockPostId, mockGiftName);
        assertEquals(mockGiftQuantity, quantityAfterInsert);
    }

    //updateReceiveGift should update the gift quantity successfully
    @Test
    public void test6() {
        int beforeUpdate = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(mockPostId, mockGiftName);

        receiveGiftJdbcDao.updateReceiveGift(mockUpdateQuantity, mockPostId, mockGiftName);

        int afterUpdate = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(mockPostId, mockGiftName);

        assertEquals(afterUpdate - beforeUpdate, mockUpdateQuantity);
    }

    //deleteReceiveGift should delete the gift successfully
    @Test
    public void test7() {
        int beforeDelete = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(mockPostId, mockGiftName);
        assertNotEquals(0, beforeDelete);

        receiveGiftJdbcDao.deleteReceiveGift(mockPostId, mockGiftName);

        int afterDelete = receiveGiftJdbcDao.getGiftQuantityFromPostIdAndGiftName(mockPostId, mockGiftName);
        assertEquals(0, afterDelete);

    }


}
