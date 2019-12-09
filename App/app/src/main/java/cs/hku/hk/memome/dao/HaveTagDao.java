package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.HaveTag;

public interface HaveTagDao {

    String getTagNameByPostId(String postId);

    void insertHaveTag(HaveTag haveTag);

    void deleteHaveTag(String postId);

}
