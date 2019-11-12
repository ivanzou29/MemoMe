package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.HaveTag;

public interface HaveTagDao {

    Collection<String> getTagNamesByPostId(String postId);

    void insertHaveTag(HaveTag haveTag);

    void deleteHaveTag(String postId);

}
