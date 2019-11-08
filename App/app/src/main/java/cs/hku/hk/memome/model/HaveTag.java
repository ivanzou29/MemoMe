package cs.hku.hk.memome.model;

public class HaveTag {

    private String postId;
    private String tagName;

    public HaveTag (String postId, String tagName) {
        this.postId = postId;
        this.tagName = tagName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
