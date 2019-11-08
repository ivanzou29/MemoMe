package cs.hku.hk.memome.model;

public class Own {

    private String email;
    private String giftName;
    private int quantity;

    public Own(String email, String giftName, int quantity) {
        this.email = email;
        this.giftName = giftName;
        this.quantity = quantity;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
