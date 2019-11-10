package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Image;

public interface ImageDao {

    Collection<byte[]> getImagesByPostId(String postId);

    void insertImage(Image image);

    void deleteImagesByPostId(String postId);

}
