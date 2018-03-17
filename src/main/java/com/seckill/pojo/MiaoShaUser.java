package com.seckill.pojo;

import java.util.Date;

public class MiaoShaUser {
    private Long id;

    private String nickname;

    private String password;

    private String salt;

    private String head;

    private Date registerData;

    private Date lastLoginDate;

    private Integer loginCount;

    public MiaoShaUser(Long id, String nickname, String password, String salt, String head, Date registerData, Date lastLoginDate, Integer loginCount) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.salt = salt;
        this.head = head;
        this.registerData = registerData;
        this.lastLoginDate = lastLoginDate;
        this.loginCount = loginCount;
    }

    public MiaoShaUser() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public Date getRegisterData() {
        return registerData;
    }

    public void setRegisterData(Date registerData) {
        this.registerData = registerData;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}