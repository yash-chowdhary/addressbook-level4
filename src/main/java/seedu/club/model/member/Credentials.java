package seedu.club.model.member;
//@@author th14thmusician
/**
 * Stores the username and password for a specific member
 */
public class Credentials {
    private Username username;
    private Password password;

    public Credentials(Username username) {
        this.username = username;
        this.password = new Password("password");
    }

    public Credentials(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValid(String username, String password) {
        return password.equals(this.password.value) && username.equals(this.username.value);
    }

    public Username getUsername() {
        return this.username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
//@@author
