package com.example.wzh.dadabike.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wzh.dadabike.R;

/**
 * Author by wzh,Date on 2019/3/22.
 * PS: Not easy to write code, please indicate.
 */
public class CustomDialog extends Dialog implements View.OnClickListener{

    private TextView tv_title,tv_massage,btn_quxiao,btn_queding;
    private String title,massage,quxiao,queding;
    private IquxiaoListener quxiaoListener;
    private IquedingListener quedingListener;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public void setQuxiao(String quxiao, IquxiaoListener listener) {
        this.quxiao = quxiao;
        this.quxiaoListener = listener;
    }

    public void setQueding(String queding, IquedingListener listener) {this.queding = queding;
        this.quedingListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_customdialog);

        //获取屏幕宽度，设置提示框的大小
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();
        display.getSize(size);
        params.width = (int)(size.x*0.8);//设置dialog的宽度为当前手机屏幕80%的宽度
        getWindow().setAttributes(params);

        tv_title = findViewById(R.id.tv_title);
        tv_massage = findViewById(R.id.tv_massage);
        btn_quxiao = findViewById(R.id.btn_quxiao);
        btn_queding = findViewById(R.id.btn_queding);
        //如果 title不为空，将title的值赋予tv_title
        if (title != null){
            tv_title.setText(title);
        }
        if (massage != null){
            tv_massage.setText(massage);
        }
        if (quxiao != null){
            btn_quxiao.setText(quxiao);
        }
        if (queding != null){
            btn_queding.setText(queding);
        }
        btn_quxiao.setOnClickListener(this);
        btn_queding.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_quxiao:
                if (quxiaoListener != null){
                    quxiaoListener.quxiao(this);
                    dismiss();
                }
                break;
            case R.id.btn_queding:
                if(quedingListener != null){
                    quedingListener.queding(this);
                    dismiss();
                }
                break;
        }
    }


    //按钮监听，设置接口
    //取消按钮
    public interface IquxiaoListener{
        void quxiao(CustomDialog dialog);
    }
    //确定按钮
    public interface IquedingListener{
        void queding(CustomDialog dialog);
    }
}
