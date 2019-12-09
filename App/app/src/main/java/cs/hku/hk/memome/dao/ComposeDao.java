package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Compose;

public interface ComposeDao {

    Collection<String> getPostIdsByEmail(String email);

    void insertCompose(Compose compose);

    void deleteCompose(String postId);

    String getUserEmailByPostId(String postId);

}
