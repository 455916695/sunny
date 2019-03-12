package com.ax.entity;

import com.ax.pojo.TbImage;
import com.ax.pojo.TbUser;

import java.util.Date;

public class User extends TbUser {
    private Long id;
    private String username;
    private String password;
    private String name;
    private Date birthday;
    private String school;
    private String address;
    private Integer sex;
    private String email;
    private String wechat;
    private String phone;
    private Integer totalScore;
    private Integer residueScore;
    private Integer empiricalValue;
    private Integer topScores;

    private TbImage image;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String getSchool() {
        return school;
    }

    @Override
    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Integer getSex() {
        return sex;
    }

    @Override
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getWechat() {
        return wechat;
    }

    @Override
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Integer getTotalScore() {
        return totalScore;
    }

    @Override
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public Integer getResidueScore() {
        return residueScore;
    }

    @Override
    public void setResidueScore(Integer residueScore) {
        this.residueScore = residueScore;
    }

    @Override
    public Integer getEmpiricalValue() {
        return empiricalValue;
    }

    @Override
    public void setEmpiricalValue(Integer empiricalValue) {
        this.empiricalValue = empiricalValue;
    }

    @Override
    public Integer getTopScores() {
        return topScores;
    }

    @Override
    public void setTopScores(Integer topScores) {
        this.topScores = topScores;
    }

    public TbImage getImage() {
        return image;
    }

    public void setImage(TbImage image) {
        this.image = image;
    }
}
