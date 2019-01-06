package kr.mitgames.codingteacher.user;

public class EndUserDTO {
    private String loginId;
    private String emailAccount;
    private String password;

    public String getLoginId() {
        return loginId;
    }
    public String getEmailAccount() {
        return emailAccount;
    }
    public String getPassword() {
        return password;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public void setEmailAccount(String email) {
        this.emailAccount = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
