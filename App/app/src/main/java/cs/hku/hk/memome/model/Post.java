package cs.hku.hk.memome.model;

public class Post {

    private String postId;
    private boolean isPublic;
    private String text;
    private String title;

    public Post (String postId, boolean isPublic, String text, String title) {
        this.postId = postId;
        this.isPublic = isPublic;
        this.text = text;
        this.title = title;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean getPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
