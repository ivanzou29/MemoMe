package cs.hku.hk.memome.model;

public class User {

    private String email;
    private int coin;
    private String passcode;
    private byte[] profilePhoto;
    private String username;

    public User(String email, int coin, String passcode, byte[] profilePhoto, String username) {
        this.email = email;
        this.coin = coin;
        this.passcode = passcode;
        this.profilePhoto = profilePhoto;
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCoin() {
        return this.coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public byte[] getProfilePhoto() {
        return this.profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
