package com.example.wzh.dadabike.bean;

import cn.bmob.v3.BmobObject;

/**
 * Author by wzh,Date on 2019/3/22.
 * PS: Not easy to write code, please indicate.
 */
public class BikeMessage extends BmobObject{

    private String bikenumber;

    public String getBikenumber() {
        return bikenumber;
    }

    public void setBikenumber(String bikenumber) {
        this.bikenumber = bikenumber;
    }
}
