package com.example.wzh.dadabike.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.BmobObject;

/**
 * Author by wzh,Date on 2019/3/22.
 * PS: Not easy to write code, please indicate.
 */
public class Bill extends BmobObject{

    private String username;
    private String bikenumber;
    private int time;
    private int money;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBikenumber() {
        return bikenumber;
    }

    public void setBikenumber(String bikenumber) {
        this.bikenumber = bikenumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}
