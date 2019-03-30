package com.example.wzh.dadabike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.bean.Balance;

import java.util.List;

/**
 * Author by wzh,Date on 2019/3/22.
 * PS: Not easy to write code, please indicate.
 */
public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Balance> list;
    private LayoutInflater layoutInflater;
    private int money;

    public ListViewAdapter() {
    }

    public ListViewAdapter(Context context, List<Balance> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.listview_item,null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_money);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        for (Balance balance : list){
            money += balance.getBalance();
        }
        holder.textView.setText(money+"元");
        return convertView;
    }
}
