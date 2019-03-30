package com.example.wzh.dadabike.fragment;


import android.accessibilityservice.GestureDescription;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.activity.RechargeActivity;
import com.example.wzh.dadabike.bean.Balance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {

    private Button button;      //充值按钮
    private ImageView iv_cardpackage,iv_yearcard,iv_coupon; //卡包，年卡，优惠券
    TextView textView1;
String TAG="c256";
    int sum;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView1 = view.findViewById(R.id.tv_money);
        button = view.findViewById(R.id.button);
        iv_cardpackage = view.findViewById(R.id.iv_cardpackage);
        iv_yearcard = view.findViewById(R.id.iv_yearcard);
        iv_coupon = view.findViewById(R.id.iv_coupon);


        //Bmob初始化
        Bmob.initialize(getActivity(),"787daaef44687d64da65e947ca61ffcd");

        //充值事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BmobQuery<Balance> bmobQuery = new BmobQuery<Balance>();
                bmobQuery.sum(new String[]{"balance"});
                bmobQuery.findStatistics(Balance.class, new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray jsonArray, BmobException e) {
                        if (e == null) {
                      //      Snackbar.make(mBtnStatistics, "查询成功：" + jsonArray.length(), Snackbar.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                sum= jsonObject.getInt("_sumBalance");
                                Log.i(TAG, "done: "+sum);
                              //  textView1.setText(sum);
                            //    Snackbar.make(mBtnStatistics, "sum：" + sum, Snackbar.LENGTH_LONG).show();
                          //      textView1.setText(sum);

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Log.e("BMOB", e.toString());
                         //   Snackbar.make(mBtnStatistics, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
              //  textView1.setText(sum);
                Intent intent = new Intent(getActivity(),RechargeActivity.class);
                startActivityForResult(intent,100);

             //
            }
        });
    }

}
