package lv.javaguru.java2.servlet.mvc.data;

public class LoginControllerData {
        String login = "";
        String password = "/";

    public LoginControllerData() {
    }

    public LoginControllerData(String login, String password) {
            this.login = login;
            this.password = password;
        }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
