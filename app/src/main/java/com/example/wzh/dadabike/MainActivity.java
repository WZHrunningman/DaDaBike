package com.example.wzh.dadabike;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.wzh.dadabike.activity.NavigationViewActivity;
import com.example.wzh.dadabike.adapter.ListViewAdapter;
import com.example.wzh.dadabike.bean.Balance;
import com.example.wzh.dadabike.fragment.PersonFragment;
import com.example.wzh.dadabike.fragment.UseBikeFragment;
import com.example.wzh.dadabike.fragment.WalletFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {

    private NavigationViewActivity nv;
    private UseBikeFragment useBikeFragment;
    private WalletFragment walleFragment;
    private PersonFragment personFragment;
    private String[] titles = {"出行","钱包","我的"};
    private int[] selectedImage = {R.drawable.bike_select,R.drawable.money_select,R.drawable.me_select};
    private int[] unSelectedImage = {R.drawable.bike1,R.drawable.money,R.drawable.me};
    private int mHeight;
    private boolean isGetHeight = true;
    public long exitTime = 0;      //按下两次返回键的间隔时长
    private FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private List<Balance> data;
    private int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        nv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showFragment(position);
            }
        });

        if (useBikeFragment == null){
            useBikeFragment = new UseBikeFragment();
        }
    }

    /**
     * 这里是用下载从其他activity跳回fragment的方法,paymentActivity跳回usebikefragment
     */
    public void gotoUseBikeFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        UseBikeFragment useBikeFragment = new UseBikeFragment();
        fragmentTransaction.replace(R.id.main_f1,useBikeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    /**
     * 这里是用下载从其他activity跳回fragment的方法 ，rechargeactivity跳回walletfragment
     */
    public void gotoWalletfragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        WalletFragment walletFragment = new WalletFragment();
        fragmentTransaction.replace(R.id.main_f1,walletFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void initView() {
        // 获取屏幕宽度
        Display dm = getWindowManager().getDefaultDisplay();
        final int screenWidth = dm.getWidth();
        nv = (NavigationViewActivity) findViewById(R.id.nv);
        // 初始化获取底部导航自身高度
        final ViewTreeObserver vt = nv.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isGetHeight) {
                    mHeight = nv.getMeasuredHeight();
                    nv.setLayout(titles, selectedImage, unSelectedImage, screenWidth, mHeight, MainActivity.this);
                    nv.setColorLing(0);
                    isGetHeight = false;
                }
                return true;
            }
        });
        showFragment(0);
    }

    /**
     * 动态添加和显示fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (useBikeFragment == null) {
                    useBikeFragment = new UseBikeFragment();
                    transaction.add(R.id.main_f1, useBikeFragment);
                } else {
                    transaction.show(useBikeFragment);
                }
                break;
            case 1:
                if (walleFragment == null) {
                    walleFragment = new WalletFragment();
                    transaction.add(R.id.main_f1, walleFragment);
                } else {
                    transaction.show(walleFragment);
                }
                break;

            case 2:
                if (personFragment == null){
                    personFragment = new PersonFragment();
                    transaction.add(R.id.main_f1,personFragment);
                } else {
                    transaction.show(personFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (useBikeFragment != null) {
            transaction.hide(useBikeFragment);
        }

        if (walleFragment != null) {
            transaction.hide(walleFragment);
        }

        if (personFragment != null){
            transaction.hide(personFragment);
        }
    }

    /**
     * //禁止使用返回键返回到上一页,但是按两下可以直接退出程序**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event); //继续执行父类其他点击事件
    }

    private void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
            System.exit(0);
        }

    }

//    public void getData(){
//        /**
//         * 余额的显示
//         * 直接查数据库余额列，然后取出来，相加
//         * 查询取值
//         */
//        data = new ArrayList<Balance>();
//        BmobQuery<Balance> query = new BmobQuery<Balance>();
//        query.addQueryKeys("balance");
//        query.findObjects(new FindListener<Balance>() {
//            @Override
//            public void done(List<Balance> list, BmobException e) {
//                if (e == null){
//                    WalletFragment walletFragment = new WalletFragment();
//                    for (Balance balance : list){
//                        balance.getBalance();
//                        data.add(balance);
//                    }
//                    Log.d("money", "-------查到了"+money);
//                    ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.this,data);
//                    walletFragment.listView.setAdapter(listViewAdapter);
//                }
//                else {
//                    Log.d("money", "-------查不到"+money);
//                }
//            }
//        });
//    }

}
