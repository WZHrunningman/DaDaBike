package com.example.wzh.dadabike;

/**
 * Author by wzh,Date on 2019/3/22.
 * PS: Not easy to write code, please indicate.
 */
public class RecycleViewItem {

    int photo;
    String where;

    public RecycleViewItem() {
    }

    public RecycleViewItem(int photo, String where) {
        this.photo = photo;
        this.where = where;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
