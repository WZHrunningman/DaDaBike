package com.example.wzh.dadabike.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.services.route.RouteSearch;
import com.example.wzh.dadabike.MainActivity;
import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.activity.PaymentActivity;
import com.example.wzh.dadabike.activity.RechargeActivity;
import com.example.wzh.dadabike.activity.StrokeActivity;
import com.example.wzh.dadabike.bean.BikeMessage;
import com.example.wzh.dadabike.bean.Bill;
import com.example.wzh.dadabike.customdialog.CustomDialog;
import com.example.wzh.dadabike.utils.Utils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class UseBikeFragment extends Fragment implements View.OnClickListener{

    private MapView mapView = null;
    private AMap aMap;
    private UiSettings uiSettings;  //定义一个UiSettings对象
    private RouteSearch routeSearch;    //导航用的对象
    private EditText ed_input;
    private Button btn_startuse;
    private TextView tv_bikemessage,tv_time,tv_minute,tv_second;
    private ImageView iv_scan_code,iv_navigation;
    private int recLen = 0,minute = 0,time = 0;     //计时器秒分时
    public static int flag = 0;     //判断按钮的两次点击
    private LinearLayout ll_time;
    public static int i = 0;    //判断toast是否弹出
    private AMapNavi aMapNavi;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_use_bike, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);
        ed_input = view.findViewById(R.id.ed_input);
        btn_startuse = view.findViewById(R.id.btn_startuse);
        tv_bikemessage = view.findViewById(R.id.tv_bikemessage);
        tv_time = view.findViewById(R.id.tv_time);
        tv_minute = view.findViewById(R.id.tv_minute);
        tv_second = view.findViewById(R.id.tv_second);
        iv_scan_code = view.findViewById(R.id.iv_scan_code);
        iv_navigation = view.findViewById(R.id.iv_navigation);
        ll_time = view.findViewById(R.id.ll_time);

        //Bmob初始化
        Bmob.initialize(getActivity(),"787daaef44687d64da65e947ca61ffcd");

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //实例化UiSettings类对象
        uiSettings = aMap.getUiSettings();
        //地图指南针显示
        uiSettings.setCompassEnabled(true);
        //控制比例尺控件是否显示
        uiSettings.setScaleControlsEnabled(true);
        /**
         * 实现地图蓝点，即定位
         */
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.interval(8000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        /**
         * 开始导航
         */



        iv_scan_code.setOnClickListener(this);
        btn_startuse.setOnClickListener(this);
        iv_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 导航组件
                 */
                AmapNaviPage.getInstance().showRouteActivity(getActivity(), new AmapNaviParams(null), new INaviInfoCallback() {
                    @Override
                    public void onInitNaviFailure() {

                    }

                    @Override
                    public void onGetNavigationText(String s) {

                    }

                    @Override
                    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

                    }

                    @Override
                    public void onArriveDestination(boolean b) {

                    }

                    @Override
                    public void onStartNavi(int i) {
                    }

                    @Override
                    public void onCalculateRouteSuccess(int[] ints) {

                    }

                    @Override
                    public void onCalculateRouteFailure(int i) {

                    }

                    @Override
                    public void onStopSpeaking() {

                    }

                    @Override
                    public void onReCalculateRoute(int i) {

                    }

                    @Override
                    public void onExitPage(int i) {

                    }

                    @Override
                    public void onStrategyChanged(int i) {

                    }

                    @Override
                    public View getCustomNaviBottomView() {
                        return null;
                    }

                    @Override
                    public View getCustomNaviView() {
                        return null;
                    }

                    @Override
                    public void onArrivedWayPoint(int i) {

                    }
                });
            }
        });

        //强制隐藏输入法
        hideInput(getActivity(),ed_input);

    }


    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 扫码返回值获取
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == 1000 && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                ed_input.setText(content);
            }
        }
        if (requestCode == 1 && resultCode == 2){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.gotoUseBikeFragment();
        }
        /**
         * 这里接收的是充值界面传回来的返回值，让界面依然回到钱包界面
         * @param requestCode
         * @param resultCode
         * @param data
         */
        if (requestCode == 100 && resultCode == 200){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.gotoWalletfragment();
        }

    }
    @Override

    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()){
            /**
             * 扫码点击事件
             */
            case R.id.iv_scan_code:
                final Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 1000);
                break;
            /**
             * 开始用车
             * 达到效果：先判断按钮是借车的还是还车的，然后再判断当前的车牌号是否存在，不存在就报错
             * 这里比较复杂，首先先判断目前按钮的借车的，
             * 这里也是需要优化，加提示框，加付款界面跳转，然后再保存数据，用户信息，车辆信息，用车时长，建一个新的表
             * 这里的数据表数据和输入数据对比出现问题
             *
             * 想法：
             * shenglue.因为有代码生成表，然后再来访问表数据，不然查不到
             * 2.生成一个独立的java文件，然后在进行访问，将数据放进去，文件里面的代码是更新表数据或者新建表数据
             */
            case R.id.btn_startuse:
                if (Utils.isFastClick()){
                    final String number = ed_input.getText().toString();
                    if (flag == 0) {
                        BmobQuery<BikeMessage> query = new BmobQuery<BikeMessage>();
                        query.addQueryKeys("bikenumber");
                        query.findObjects(new FindListener<BikeMessage>() {
                            @Override
                            public void done(List<BikeMessage> list, BmobException e) {
                                if (e == null) {
                                    for (BikeMessage bikeMassage : list) {
                                        if (bikeMassage.getBikenumber().equals(number) ){
                                            CustomDialog customDialog = new CustomDialog(getActivity());
                                            customDialog.setTitle("提示");
                                            customDialog.setMassage("确定要租借"+"\n"+number+"\n"+"单车吗？");
                                            customDialog.setQueding("确定", new CustomDialog.IquedingListener() {
                                                @Override
                                                public void queding(CustomDialog dialog) {
                                                    ed_input.setVisibility(View.GONE);
                                                    tv_bikemessage.setVisibility(View.VISIBLE);
                                                    ll_time.setVisibility(View.VISIBLE);
                                                    tv_bikemessage.setText("单车编号: " + number);
                                                    btn_startuse.setText("还车结算");
                                                    //调用计时器
                                                    handler.postDelayed(runnable, 3000);
                                                    //强制隐藏输入法
                                                    hideInput(getActivity(),ed_input);
                                                    flag = 1;
                                                }
                                            });
                                            customDialog.setQuxiao("取消", new CustomDialog.IquxiaoListener() {
                                                @Override
                                                public void quxiao(CustomDialog dialog) {

                                                }
                                            });
                                            customDialog.show();
                                            break;
                                        }else{
                                            i++;
                                            if (i == list.size()){
                                                //在屏幕中间显示的toast
                                                View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_message,null);
                                                TextView textView = view.findViewById(R.id.error);
                                                //添加需要显示的信息
                                                textView.setText("请输入正确的车牌号！");
                                                //添加需要显示的信息
                                                Toast toast = new Toast(getActivity());
                                                //显示在屏幕的中间
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.setDuration(Toast.LENGTH_SHORT);
                                                toast.setView(view);
                                                toast.show();
                                                i=0;
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }else {
                        CustomDialog customDialog = new CustomDialog(getActivity());
                        customDialog.setTitle("提示");
                        customDialog.setMassage("确定还车吗？");
                        customDialog.setQueding("是的", new CustomDialog.IquedingListener() {
                            @Override
                            public void queding(CustomDialog dialog) {
                                handler.removeCallbacks(runnable);
                                ll_time.setVisibility(View.GONE);
                                tv_bikemessage.setVisibility(View.GONE);
                                ed_input.setVisibility(View.VISIBLE);
                                ed_input.setText(null);
                                ed_input.setCursorVisible(false);   //隐藏光标
                                btn_startuse.setText("开始用车");
                                flag = 0;
                                //添加数据
                                Bill bill = new Bill();
                                BmobUser bmobUser = new BmobUser();      //从本地缓存中获取当前登陆用户某一列的值。其中key为用户表的指定列名。
                                String username = bmobUser.getUsername();
                                //计算租车时长
                                int renttime = minute + (time*60);
                                //计算租金
                                int money = (time/60)*500;
                                //保存以下数据到Bmob
                                bill.setUsername(username);
                                bill.setTime(renttime);
                                bill.setBikenumber(number);
                                bill.setMoney(money);
                                bill.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {

                                    }
                                });
                                Intent intent1 = new Intent(getActivity(),PaymentActivity.class);
                                startActivityForResult(intent1,1);
                            }
                        });
                        customDialog.setQuxiao("不还", new CustomDialog.IquxiaoListener() {
                            @Override
                            public void quxiao(CustomDialog dialog) {
                            }
                        });
                        customDialog.show();
                    }
                }
                break;


        }
    }

    /**
     * 计时器
     * 计时器存在bug，修改办法，照搬假期那个模式
     */
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen < 60){
                recLen++;
                if (recLen < 10){
                    tv_second.setText("0"+recLen);
                }else {
                    tv_second.setText(""+recLen);
                }
            }else{
                minute += 1;
                if (minute < 10){
                    tv_minute.setText("0"+minute);
                }else {
                    tv_minute.setText(""+minute);
                }
                recLen = 0;
            }
            if (minute == 60){
                time += 1;
                if (time < 10){
                    tv_time.setText("0"+time);
                }else {
                    tv_time.setText(""+time);
                }
                minute = 0;
            }
            handler.postDelayed(this,10);
        }
    };
    /**
     * 强制隐藏输入法键盘
     *
     * @param context Context
     * @param view    EditText
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
