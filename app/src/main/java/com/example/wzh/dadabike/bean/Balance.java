package com.example.wzh.dadabike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Author by wzh,Date on 2019/3/22.
 * PS: Not easy to write code, please indicate.
 */
public class Balance extends BmobObject{

    private String username;
    private int balance;    //y余额
    private boolean yearcard;   //年卡
    private String coupon;      //优惠券

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isYearcard() {
        return yearcard;
    }

    public void setYearcard(boolean yearcard) {
        this.yearcard = yearcard;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

}
