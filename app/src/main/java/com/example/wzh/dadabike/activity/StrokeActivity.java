package com.example.wzh.dadabike.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.bean.Bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StrokeActivity extends AppCompatActivity {

    ListView lv_stroke;
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke);

        lv_stroke = findViewById(R.id.lv_stroke);

        //Bmob初始化
        Bmob.initialize(StrokeActivity.this,"787daaef44687d64da65e947ca61ffcd");
        BmobQuery<Bill> query = new BmobQuery<Bill>();
        final List<Map<String , String>> mapList = new ArrayList<>();
        query.findObjects(new FindListener<Bill>() {
            @Override
            public void done(List<Bill> list, BmobException e) {
                for (Bill bill : list) {
                    hashMap = new HashMap<>();
                    hashMap.put("money", "消费金额："+String.valueOf(bill.getMoney()));
                    hashMap.put("number", "车牌号："+bill.getBikenumber());
                    hashMap.put("objectid", "订单号："+bill.getObjectId());
                    hashMap.put("time", "使用时间："+bill.getCreatedAt());
                    mapList.add(hashMap);
                }
                for (int i = 0 ; i < mapList.size() ; i++){
                    SimpleAdapter simpleAdapter = new SimpleAdapter(StrokeActivity.this,mapList,R.layout.lv_stroke_item,
                            new String[]{"money","number","objectid","time"},new int[]{R.id.tv_money,R.id.tv_number,R.id.tv_objectid,
                            R.id.tv_time});
                    lv_stroke.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();
                }


            }

        });
    }
}
