package Models.Blueprint;

public class Setting {

    //Data Fields
    private String password;
    private boolean isLock = false;

    // Constructor
    public Setting(String password, boolean isLock) {
        this.password = password;
        this.isLock = isLock;
    }

    // Getter
    public String getPassword() {
        return password;
    }
    public boolean isLock() {
        return isLock;
    }

    // Method
    public boolean isPasswordSafe() {
        String pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        return this.password.matches(pattern);
    }
}