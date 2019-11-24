package cs.hku.hk.memome.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cs.hku.hk.memome.model.Post;

public interface PostDao {

    Post getPostByPostId(String postId);

    Post getPostByPostTitle(String title);

    void insertPost(Post post);

    void deletePost(String postId);

    ArrayList<Post> getAllNewPost();

    ArrayList<Post> getAllHotPost();

    void updateLikeByTitle(int like,String title);


}
