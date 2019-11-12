package cs.hku.hk.memome.model;

public class ReceiveGift {

    private String giftName;
    private String postId;
    private int quantity;

    public ReceiveGift (String giftName, String postId, int quantity) {
        this.giftName = giftName;
        this.postId = postId;
        this.quantity = quantity;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
