package kr.mitgames.codingteacher.user;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "account_id")
    private Integer id;

    @Column(unique=true, nullable=false)
    private String loginId;
    private String emailAccount;
    private String passwordHash;
    private Integer permissionLevel;
    private Integer studyLevel;

    public Integer getId() {
        return id;
    }
    public String getLoginId() {
        return loginId;
    }
    public String getEmailAccount() {
        return emailAccount;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public Integer getPermissionLevel() {
        return permissionLevel;
    }
    public Integer getStudyLevel() {
        return studyLevel;
    }


    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public void setEmailAccount(String email) {
        this.emailAccount = email;
    }
    public void setPasswordHash(String password) {
        // hash(password)
        this.passwordHash = password;
    }
    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
    public void setStudyLevel(int studyLevel) {
        this.studyLevel = studyLevel;
    }
}