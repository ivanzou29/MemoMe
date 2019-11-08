package cs.hku.hk.memome.model;

public class Favorite {

    private String email;
    private String postId;

    public Favorite(String email, String postId) {
        this.email = email;
        this.postId = postId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
