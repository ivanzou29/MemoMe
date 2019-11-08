package cs.hku.hk.memome.model;

public class Image {

    private byte[] imageBytearray;
    private int imageId;
    private String postId;

    public Image(byte[] imageBytearray, int imageId, String postId) {
        this.imageBytearray = imageBytearray;
        this.imageId = imageId;
        this.postId = postId;
    }

    public byte[] getImageBytearray() {
        return this.imageBytearray;
    }

    public void setImageBytearray(byte[] imageBytearray) {
        this.imageBytearray = imageBytearray;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
