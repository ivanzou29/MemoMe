package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.ReceiveGift;

public interface ReceiveGiftDao {

    Collection<ReceiveGift> getReceiveGiftsByPostId(String postId);

    int getGiftQuantityFromPostIdAndGiftName(String postId, String giftName);

    void insertReceiveGift(ReceiveGift receiveGift);

    void updateReceiveGift(int quantity, String postId, String giftName);

    void deleteReceiveGift(String postId, String giftName);

}
