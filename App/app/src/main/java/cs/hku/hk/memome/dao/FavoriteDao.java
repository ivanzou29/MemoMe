package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Favorite;

public interface FavoriteDao {

    Collection<String> getPostIdsByEmail(String email);

    void insertFavorite(Favorite favorite);

    void deleteFavorite(Favorite favorite);

}
