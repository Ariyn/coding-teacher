package kr.mitgames.codingteacher.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.hash.Hashing;
import kr.mitgames.codingteacher.Application;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name="users")
public class EndUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "account_id")
    private Integer id;

    @Column(unique=true, nullable=false)
    private String loginId;
    @Column(nullable=false)
    private String emailAccount;
    @Column(nullable=false)
    private String password;
    private Integer permissionLevel=0;
    private Integer studyLevel=0;
    private Integer points=0;
    private boolean emailCertificated=false;

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    public String getLoginId() {
        return loginId;
    }
    public String getEmailAccount() {
        return emailAccount;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    public Integer getPermissionLevel() {
        return permissionLevel;
    }
    @JsonIgnore
    public Integer getStudyLevel() {
        return studyLevel;
    }
    public Integer getPoints() {
        return points;
    }


    public void setLoginId(String loginId) {
        Application.LOG.warning("[user] set loginId from "+this.loginId+" to "+loginId);
        this.loginId = loginId;
    }
    public void setEmailAccount(String email) {
        this.emailAccount = email;
    }
    public void setPassword(String password) {
//        this.password = EndUser.hash(password);
        Application.LOG.warning("[user] set password from "+this.password+" to "+password);
        this.password = password;
    }
    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
    public void setStudyLevel(int studyLevel) {
        this.studyLevel = studyLevel;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public void cast(EndUserDTO userDto) {
        String loginId = userDto.getLoginId();
        String password = userDto.getPassword();
        String email = userDto.getEmailAccount();

        this.setLoginId(loginId);
        this.setPassword(password);
        this.setEmailAccount(email);
    }

    public static String hash(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}