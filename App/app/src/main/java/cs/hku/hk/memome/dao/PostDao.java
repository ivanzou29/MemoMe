package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Post;

public interface PostDao {

    Post getPostByPostId(String postId);

    Post getPostByPostTitle(String title);

    void insertPost(Post post);

    void deletePost(String postId);
}
