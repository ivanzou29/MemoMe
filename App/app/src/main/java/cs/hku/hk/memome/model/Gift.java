package cs.hku.hk.memome.model;

public class Gift {

    private String giftName;
    private int value;

    public Gift(String giftName, int value) {
        this.giftName = giftName;
        this.value = value;
    }

    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
