package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Own;

public interface OwnDao {

    Collection<Own> getOwnsByEmail(String email);

    void insertGiftOwnership(Own own);

    void updateGiftOwnership(Own own);

    void deleteGiftOwnership(String email, String giftName);

}
