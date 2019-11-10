package cs.hku.hk.memome.dao;

import cs.hku.hk.memome.model.Hashtag;

public interface HashtagDao {

    void insertHashtag(Hashtag hashtag);

    void deleteHashtag(String tagName);

}
