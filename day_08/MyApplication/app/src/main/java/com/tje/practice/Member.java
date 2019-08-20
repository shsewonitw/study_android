package com.tje.practice;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {
    private String member_id;
    private String password;
    private String name;
    private int gender;
    private Date birth;
    private String tel;
    private String email;
    private Date regist_date;
    private int level;

    public Member() {
    }

    public Member(String member_id, String password, String name, int gender, Date birth, String tel, String email,
                  Date regist_date, int level) {
        super();
        this.member_id = member_id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.tel = tel;
        this.email = email;
        this.regist_date = regist_date;
        this.level = level;
    }

    public final String getMember_id() {
        return member_id;
    }

    public final void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public String getGenderString() {
        return gender == 1 ? "여성" : "남성";
    }

    public final int getGender() {
        return gender;
    }

    public final void setGender(int gender) {
        this.gender = gender;
    }


    public final Date getBirth() {
        return birth;
    }


    public final void setBirth(Date birth) {
        this.birth = birth;
    }

    public final String getTel() {
        return tel;
    }

    public final void setTel(String tel) {
        this.tel = tel;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }


    public final Date getRegist_date() {
        return regist_date;
    }

    public final void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    public final int getLevel() {
        return level;
    }

    public final void setLevel(int level) {
        this.level = level;
    }
}

