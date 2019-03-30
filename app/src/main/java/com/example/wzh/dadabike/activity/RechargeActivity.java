package com.example.wzh.dadabike.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.bean.Balance;
import com.example.wzh.dadabike.bean.Bill;
import com.example.wzh.dadabike.register_and_login.RegisterActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_one,iv_two,iv_three,iv_four;           //五元,十元,五十元，一百元
    private Button button;      //充值
    private final String items[] = {"微信充值", "支付宝充值", "银行卡充值"};
    TextView money1;
    private AlertDialog dialog;
    private int who = 0;

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        button = findViewById(R.id.button2);
        iv_one = findViewById(R.id.iv_one);
        iv_three = findViewById(R.id.iv_three);
        iv_two  = findViewById(R.id.iv_two);
        iv_four = findViewById(R.id.iv_four);
        money1=findViewById(R.id.tv_money);

        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        iv_three.setOnClickListener(this);
        iv_four.setOnClickListener(this);

        //Bmob初始化
        Bmob.initialize(RechargeActivity.this,"787daaef44687d64da65e947ca61ffcd");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (getWho()){
                    case 1:
                        /**
                         * 选择支付对话框
                         * 这个地方可以选择将这个提示框封装，可以省代码
                         */

                        dialog = new AlertDialog.Builder(RechargeActivity.this)
                                .setIcon(R.mipmap.icon_pay)//设置标题的图片
                                .setTitle("选择充值方式")//设置对话框的标题
                                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Bill bill = new Bill();
                                        BmobUser bmobUser = new BmobUser();      //从本地缓存中获取当前登陆用户某一列的值。其中key为用户表的指定列名。
                                        String username = bmobUser.getUsername();
                                        Balance balance = new Balance();
                                        balance.setUsername(username);
                                        //取出余额
                                        final int money = balance.getBalance();
                                        balance.setBalance(5+money);

                                        balance.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                int test=50;
                                                if (e == null) {
                                                    Toast.makeText(RechargeActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                                                    money1.setText("gg");
                                                    Log.i("cc", "done: "+test);

                                                }else {
                                                    Toast.makeText(RechargeActivity.this,"充值失败"+e,Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                        setResult(200);
                                        finish();
                                    }
                                }).create();
                        dialog.show();
                        break;
                    case 2:
                        /**
                         * 选择支付对话框
                         * 这个地方可以选择将这个提示框封装，可以省代码
                         */
                        dialog = new AlertDialog.Builder(RechargeActivity.this)
                                .setIcon(R.mipmap.icon_pay)//设置标题的图片
                                .setTitle("选择充值方式")//设置对话框的标题
                                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Bill bill = new Bill();
                                        BmobUser bmobUser = new BmobUser();      //从本地缓存中获取当前登陆用户某一列的值。其中key为用户表的指定列名。
                                        String username = bmobUser.getUsername();
                                        Balance balance = new Balance();
                                        balance.setUsername(username);
                                        //取出余额
                                        int money = balance.getBalance();
                                        balance.setBalance(20+money);
                                        balance.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(RechargeActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(RechargeActivity.this,"充值失败"+e,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        setResult(200);
                                        finish();
                                    }
                                }).create();
                        dialog.show();
                        break;
                    case 3:
                        /**
                         * 选择支付对话框
                         * 这个地方可以选择将这个提示框封装，可以省代码
                         */
                        dialog = new AlertDialog.Builder(RechargeActivity.this)
                                .setIcon(R.mipmap.icon_pay)//设置标题的图片
                                .setTitle("选择充值方式")//设置对话框的标题
                                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        BmobUser bmobUser = new BmobUser();      //从本地缓存中获取当前登陆用户某一列的值。其中key为用户表的指定列名。
                                        String username = bmobUser.getUsername();
                                        Balance balance = new Balance();
                                        balance.setUsername(username);
                                        //取出余额
                                        int money = balance.getBalance();
                                        balance.setBalance(50+money);
                                        balance.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(RechargeActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(RechargeActivity.this,"充值失败"+e,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        setResult(200);
                                        finish();
                                    }
                                }).create();
                        dialog.show();
                        break;
                    case 4:
                        /**
                         * 选择支付对话框
                         * 这个地方可以选择将这个提示框封装，可以省代码
                         */
                        dialog = new AlertDialog.Builder(RechargeActivity.this)
                                .setIcon(R.mipmap.icon_pay)//设置标题的图片
                                .setTitle("选择充值方式")//设置对话框的标题
                                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Bill bill = new Bill();
                                        BmobUser bmobUser = new BmobUser();      //从本地缓存中获取当前登陆用户某一列的值。其中key为用户表的指定列名。
                                        String username = bmobUser.getUsername();
                                        Balance balance = new Balance();
                                        balance.setUsername(username);
                                        //取出余额
                                        int money = balance.getBalance();
                                        balance.setBalance(100+money);
                                        balance.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(RechargeActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(RechargeActivity.this,"充值失败"+e,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        setResult(200);
                                        finish();
                                    }
                                }).create();
                        dialog.show();
                        break;
                    default:
                        Toast.makeText(RechargeActivity.this,"请选择要充值的金额！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_one:
                iv_one.setBackgroundResource(R.drawable.bg_recharge_image_ispress);
                iv_two.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_three.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_four.setBackgroundResource(R.drawable.bg_recharge_image);
                setWho(1);
                break;
            case R.id.iv_two:
                iv_two.setBackgroundResource(R.drawable.bg_recharge_image_ispress);
                iv_one.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_three.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_four.setBackgroundResource(R.drawable.bg_recharge_image);
                setWho(2);
                break;
            case R.id.iv_three:
                iv_two.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_one.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_three.setBackgroundResource(R.drawable.bg_recharge_image_ispress);
                iv_four.setBackgroundResource(R.drawable.bg_recharge_image);
                setWho(3);
                break;
            case R.id.iv_four:
                iv_two.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_one.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_three.setBackgroundResource(R.drawable.bg_recharge_image);
                iv_four.setBackgroundResource(R.drawable.bg_recharge_image_ispress);
                setWho(4);
                break;
        }
    }
}
